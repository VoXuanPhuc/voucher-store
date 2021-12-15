import { Component, Input, OnChanges, OnInit, SimpleChanges } from '@angular/core';
import { CartService } from 'app/entities/my-cart/cart.service';
import { ICartVoucher } from 'app/entities/my-cart/CartVoucher.model';

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
    constructor(private cartService: CartService) {
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
}
