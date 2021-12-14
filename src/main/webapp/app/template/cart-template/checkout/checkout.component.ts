import { Component, Input, OnChanges, SimpleChanges } from '@angular/core';
import { ICartVoucher } from 'app/entities/my-cart/CartVoucher.model';

@Component({
    selector: 'jhi-checkout',
    templateUrl: './checkout.component.html',
    styleUrls: ['./checkout.component.scss'],
})
export class CheckoutComponent implements OnChanges {
    @Input() CartVoucher: ICartVoucher[];
    @Input() isCheckAll: boolean;
    @Input() checkoutIsCheckitem: boolean[];

    total: number;
    constructor() {
        this.checkoutIsCheckitem = [];
        this.CartVoucher = [];
        this.total = 0;
        this.isCheckAll = true;
        this.countTotal();
    }

    ngOnChanges(changes: SimpleChanges): void {
        this.countTotalAgain(changes.isCheckAll.currentValue);

        // this.countTotalitem(changes.checkoutIsCheckitem.currentValue);
    }

    countTotalAgain(isCheckAll: boolean): void {
        if (isCheckAll) {
            this.countTotal();
        } else {
            this.total = 0;
        }
    }

    countTotal(): void {
        this.CartVoucher.forEach(item => {
            this.total = this.total + item.voucher!.price!;
        });
    }

    countTotalitem(isCheckitem: boolean[]): void {
        let i = 0;
        this.CartVoucher.forEach(item => {
            if (isCheckitem[i]) {
                this.total = this.total + item.voucher!.price!;
            }
            i++;
        });
    }
}
