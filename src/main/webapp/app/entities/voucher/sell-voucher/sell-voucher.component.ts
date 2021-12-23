import { HttpResponse } from '@angular/common/http';
import { Component, Input, OnInit, SimpleChanges } from '@angular/core';
import { EventService } from 'app/entities/event/service/event.service';
import { CartService } from 'app/entities/my-cart/cart.service';
import { CartVoucher } from 'app/entities/my-cart/CartVoucher.model';
import { VoucherImageService } from 'app/entities/voucher-image/service/voucher-image.service';
import { IVoucherImage } from 'app/entities/voucher-image/voucher-image.model';
import { VoucherService } from 'app/entities/voucher/service/voucher.service';
import Swal from 'sweetalert2';
import { EntityResponseType } from './../../province/service/province.service';
import { StoreService } from './../../store/service/store.service';
import { IVoucher } from './../voucher.model';

@Component({
    selector: 'jhi-sell-voucher-item',
    templateUrl: './sell-voucher.component.html',
    styleUrls: ['./sell-voucher.component.scss'],
})
export class SellVoucherComponent implements OnInit {
    @Input() vouchers?: IVoucher[];
    @Input() isLoading = false;

    constructor(
        private voucherService: VoucherService,
        private voucherImageService: VoucherImageService,
        private eventService: EventService,
        private storeService: StoreService,
        private cartService: CartService
    ) {}

    ngOnInit(): void {
        return;
    }

    ngOnChanges(changes: SimpleChanges): void {
        for (const property in changes) {
            if (property === 'vouchers') {
                this.loadImageAndStoreForVoucher(this.vouchers ?? []);
            }
        }
    }

    loadImageAndStoreForVoucher(vouchers: IVoucher[]): void {
        vouchers.forEach(voucher => {
            this.voucherImageService.queryByVoucherId(voucher.id ?? 0).subscribe(
                (imageRes: HttpResponse<IVoucherImage[]>) => {
                    voucher.voucherImages = imageRes.body ?? [];
                },
                () => window.console.log('An error occurs when loading image data!')
            );

            this.eventService.find(voucher.event?.id ?? 0).subscribe(
                (eventRes: EntityResponseType) => {
                    voucher.event = eventRes.body;
                    this.storeService.find(voucher.event?.store?.id ?? 1).subscribe(
                        (storeRes: EntityResponseType) => {
                            voucher.event!.store = storeRes.body;
                        },
                        () => window.console.log('An error occurs when loading store data!')
                    );
                },
                () => window.console.log('An error occurs when loading event data!')
            );
        });
    }

    addToCart(v: IVoucher): void {
        const cartVoucher = new CartVoucher(1, v);

        if (!this.cartService.checkItemInCart(cartVoucher)) {
            this.cartService.addToCart(cartVoucher);
            this.cartService.saveCart();
            this.alterSuccess();
        } else {
            this.cartService.items.map(item => {
                if (item.voucher?.id === v.id) {
                    this.cartService.deleteItem(item);
                }
            });

            // this.cartService.changeItem(5);
        }
    }

    alterSuccess(): void {
        Swal.fire({
            position: 'top',
            icon: 'success',
            title: 'Add to cart success',
            showConfirmButton: false,
            timer: 700,
        });
    }
}
