import { OrderStatus } from './../../../entities/order-status/order-status.model';
import { MyOrderService } from './../../../entities/my-order/service/my-order.service';
import { IMyOrder, MyOrder } from './../../../entities/my-order/my-order.model';
import { IVoucherCode } from './../../../entities/voucher-code/voucher-code.model';
import { VoucherCodeService } from 'app/entities/voucher-code/service/voucher-code.service';
import { CartVoucher } from './../../../entities/my-cart/CartVoucher.model';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Router } from '@angular/router';
import { MyUserService } from './../../../entities/my-user/service/my-user.service';
import { MyUser } from './../../../entities/my-user/my-user.model';
import { Component, Input, OnChanges, OnInit, SimpleChanges } from '@angular/core';
import { CartService } from 'app/entities/my-cart/cart.service';
import { ICartVoucher } from 'app/entities/my-cart/CartVoucher.model';
import Swal from 'sweetalert2';

@Component({
    selector: 'jhi-checkout',
    templateUrl: './checkout.component.html',
    styleUrls: ['./checkout.component.scss'],
})
export class CheckoutComponent implements OnChanges, OnInit {
    CartVoucher: ICartVoucher[];
    // @Input() isCheckAll: boolean;

    @Input() checkoutIsCheckitem: boolean[];

    currentChangeState: boolean[];

    total: number;

    user?: MyUser;

    constructor(
        private cartService: CartService,
        private userService: MyUserService,
        private voucherCodeService: VoucherCodeService,
        private orderService: MyOrderService,
        private route: Router
    ) {
        this.currentChangeState = [];
        this.checkoutIsCheckitem = [];
        this.CartVoucher = [];
        this.total = 0;
        // this.isCheckAll = true;
    }

    ngOnInit(): void {
        this.makeCheckAll();
        this.cartService.totalItemChange.subscribe(item => {
            window.console.log(item);
            this.loadCartVoucher();
            this.countTotalitem(this.currentChangeState);
        });
    }

    loadCartVoucher(): void {
        this.CartVoucher = this.cartService.items;
    }

    ngOnChanges(changes: SimpleChanges): void {
        // this.countTotalAgain(changes.isCheckAll.currentValue);
        this.currentChangeState = changes.checkoutIsCheckitem.currentValue;
        this.countTotalitem(changes.checkoutIsCheckitem.currentValue);
    }

    makeCheckAll(): void {
        for (let i = 0; i < 20; i++) {
            this.currentChangeState[i] = true;
        }
    }

    countTotalAgain(isCheckAll: boolean): void {
        if (isCheckAll) {
            this.countTotal();
        } else {
            this.total = 0;
        }
    }

    countTotal(): void {
        this.total = 0;
        this.CartVoucher.forEach(item => {
            this.total = this.total + item.voucher!.price! * item.total!;
        });
    }

    countTotalitem(isCheckitem: boolean[]): void {
        this.loadCartVoucher();
        let i = 0;
        this.total = 0;
        this.CartVoucher.forEach(item => {
            if (isCheckitem[i]) {
                this.total = this.total + item.voucher!.price! * item.total!;
            }
            i++;
        });
    }

    //get cart items is checked
    getCardItemIsChecked(): ICartVoucher[] {
        this.loadCartVoucher();
        let i = 0;
        const list: ICartVoucher[] = [];
        this.CartVoucher.forEach(item => {
            if (this.currentChangeState[i]) {
                list.push(item);
            }
            i++;
        });
        return list;
    }

    //remove cart items is checked from all cart items after save order successfully
    removeCartItemIsChecked(removeList: ICartVoucher[]): void {
        this.loadCartVoucher();

        window.console.log('Cart: ', this.CartVoucher);

        removeList.forEach(removeItem => {
            let index = -1;
            index = this.CartVoucher.findIndex(item => item.voucher?.id === removeItem.voucher?.id);

            if (index !== -1) {
                this.CartVoucher.splice(index, 1);
            }
        });

        this.cartService.items = this.CartVoucher;

        this.cartService.saveCart();
    }

    //get current user by jwt
    async getCurrentUser(): Promise<void> {
        try {
            const myuser = await this.userService.getUserByJWT().toPromise();
            this.user = myuser.body!;
        } catch (error) {
            if (error instanceof HttpErrorResponse) {
                this.route.navigate(['login']);
            }
            window.console.log('Errorrrrrrrrrrrrrrrr: ', error);
        }
    }

    //get list of voucher codes are in available status
    async getAvailableVoucherCode(voucherId: number, quantity: number): Promise<HttpResponse<IVoucherCode[]>> {
        const VOUCHER_CODE_AVAILABLE_STATUS = 1;

        return this.voucherCodeService
            .findByStatusIdAndVoucherIdAndQuantity(VOUCHER_CODE_AVAILABLE_STATUS, voucherId, quantity)
            .toPromise();
    }

    //update order id and status id for voucher codes
    updateOrderIdAndStatusId(voucherCodes: IVoucherCode[], orderId: number): void {
        const VOUCHER_CODE_SOLD_UNUSED_STATUS = 2;

        voucherCodes.forEach(voucherCode => {
            if (voucherCode.status?.id) {
                voucherCode.status.id = VOUCHER_CODE_SOLD_UNUSED_STATUS;
            }

            voucherCode.order = new MyOrder(orderId);
        });
    }

    //save list of voucher codes
    async saveVoucherCodeList(voucherCodes: IVoucherCode[]): Promise<void> {
        window.console.log('Voucher codess: ', voucherCodes);

        for (const voucherCode of voucherCodes) {
            await this.voucherCodeService.partialUpdate(voucherCode).toPromise();
        }
    }

    //create new order
    async saveOrder(order: IMyOrder): Promise<HttpResponse<IMyOrder>> {
        return await this.orderService.create(order).toPromise();
    }

    // save order and voucher codes to DB
    async onSaveCart(): Promise<any> {
        const cartItemIsChecked: ICartVoucher[] = this.getCardItemIsChecked();

        await this.getCurrentUser();

        if (!this.user) {
            return;
        }

        let order: IMyOrder = new MyOrder();
        order.user = this.user;
        order.totalCost = this.total;
        order.status = new OrderStatus(1);

        order = (await this.saveOrder(order)).body!;

        for (const item of cartItemIsChecked) {
            const voucherCodes = (await this.getAvailableVoucherCode(item.voucher?.id ?? 0, item.total ?? 0)).body;

            if (!voucherCodes) {
                return;
            }

            this.updateOrderIdAndStatusId(voucherCodes, order.id ?? 0);

            await this.saveVoucherCodeList(voucherCodes);
        }
        this.route.navigate(['/user/my-voucher']);
        this.removeCartItemIsChecked(cartItemIsChecked);
    }

    alerBeferCheckout(): void {
        Swal.fire({
            position: 'top',
            title: 'Are you sure you want to checkout this cart?',
            text: "You won't be able to revert this!",
            icon: 'question',
            showCancelButton: true,
            confirmButtonColor: '#02E301',
            cancelButtonColor: '#d33',
            confirmButtonText: 'Yes, checkout it!',
        }).then(result => {
            if (result.isConfirmed) {
                this.onSaveCart();

                Swal.fire({
                    position: 'top',
                    icon: 'success',
                    title: 'Checkout successfully',
                    showConfirmButton: false,
                    timer: 1500,
                });
            }
        });
    }
}
