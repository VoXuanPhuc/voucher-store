import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { EventService } from 'app/entities/event/service/event.service';
import { CartVoucher } from 'app/entities/my-cart/CartVoucher.model';
import { StoreService } from 'app/entities/store/service/store.service';
import { VoucherCodeService } from 'app/entities/voucher-code/service/voucher-code.service';
import { VoucherService } from 'app/entities/voucher/service/voucher.service';
import { IVoucher } from 'app/entities/voucher/voucher.model';
import { CartService } from '../../entities/my-cart/cart.service';

@Component({
    selector: 'jhi-detail-voucher',
    templateUrl: './detail-voucher.component.html',
    styleUrls: ['./detail-voucher.component.scss'],
})
export class DetailVoucherComponent implements OnInit {
    Voucher!: IVoucher;
    availableVoucher: any;
    id: any;
    selectedVoucher: number;
    constructor(
        private voucherServie: VoucherService,
        private route: ActivatedRoute,
        private storeService: StoreService,
        private voucherCodeService: VoucherCodeService,
        private eventService: EventService,
        private cartSerive: CartService
    ) {
        this.availableVoucher = 0;
        this.selectedVoucher = 1;
    }

    ngOnInit(): void {
        this.route.paramMap.subscribe(params => {
            this.id = params.get('id');
            this.loadVoucherById();
            this.voucherCodeService.countVoucherCode(this.id).subscribe(res => (this.availableVoucher = res.body));
        });
    }

    loadVoucherById(): void {
        this.voucherServie.find(this.id).subscribe(res => {
            this.Voucher = res.body!;
            this.eventService.find(this.Voucher.event?.id ?? 1).subscribe(resSer => {
                this.Voucher.event = resSer.body;
                this.storeService.find(this.Voucher.event?.store?.id ?? 5).subscribe(resStore => {
                    this.Voucher.event!.store = resStore.body;
                });
            });
        });
    }

    increment(): void {
        if (this.selectedVoucher >= this.availableVoucher) {
            return;
        }
        this.selectedVoucher = this.selectedVoucher + 1;
    }
    decrement(): void {
        if (this.selectedVoucher === 1) {
            return;
        }
        this.selectedVoucher = this.selectedVoucher - 1;
    }

    addToCart(voucher: IVoucher): void {
        const cartVoucher = new CartVoucher(this.selectedVoucher, voucher);

        if (!this.cartSerive.checkItemInCart(cartVoucher)) {
            this.cartSerive.addToCart(cartVoucher);
            this.cartSerive.saveCart();
        } else {
            this.cartSerive.items.map(item => {
                if (item.voucher?.id === cartVoucher.voucher?.id) {
                    if (this.availableVoucher >= item.total! + this.selectedVoucher) {
                        item.total = item.total! + this.selectedVoucher;
                    }
                }
            });
            this.cartSerive.saveCart();
        }
    }
}
