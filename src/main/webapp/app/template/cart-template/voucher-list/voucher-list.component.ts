import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { CartService } from 'app/entities/my-cart/cart.service';
import { ICartVoucher } from 'app/entities/my-cart/CartVoucher.model';
import { VoucherCodeService } from 'app/entities/voucher-code/service/voucher-code.service';
import { VoucherImageService } from 'app/entities/voucher-image/service/voucher-image.service';

@Component({
    selector: 'jhi-voucher-list',
    templateUrl: './voucher-list.component.html',
    styleUrls: ['./voucher-list.component.scss'],
})
export class VoucherListComponent implements OnInit {
    @Input() CartVoucher: ICartVoucher[];
    checkeditem: boolean[];
    isCheckedAll: boolean;
    @Output() checkedAll = new EventEmitter<boolean>();
    @Output() outputCheckItem = new EventEmitter<boolean[]>();

    voucherCode: any[];
    constructor(
        private voucherImageService: VoucherImageService,
        private cartService: CartService,
        private voucherCodeService: VoucherCodeService
    ) {
        this.CartVoucher = [];
        this.voucherCode = new Array(20);
        this.isCheckedAll = true;
        this.checkeditem = new Array(20);
    }

    ngOnInit(): void {
        this.loadImage();
        this.makeCheckAll();
        this.loadVoucherCoder();
    }

    loadVoucherCoder(): void {
        this.CartVoucher.forEach(item => {
            this.voucherCodeService.countVoucherCode(item.voucher?.id ?? 0).subscribe(res => {
                this.voucherCode.push(res.body ?? []);
            });
        });

        window.console.log(this.voucherCode);
    }

    loadImage(): void {
        this.CartVoucher.forEach(item => {
            this.voucherImageService.queryByVoucherId(item.voucher?.id ?? 0).subscribe(image => {
                item.voucher!.voucherImages = image.body;
            });
        });
    }

    makeCheckAll(): void {
        for (let i = 0; i < 20; i++) {
            this.checkeditem[i] = true;
        }
    }
    makenotCheckall(): void {
        for (let i = 0; i < 20; i++) {
            this.checkeditem[i] = false;
        }
    }

    ischeckItem(idProduct: number): void {
        for (let i = 0; i < 20; i++) {
            if (i === idProduct) {
                this.checkeditem[i] = !this.checkeditem[i];
                break;
            }
        }
        this.checkedAll.emit(!this.isCheckedAll);
        this.outputCheckItem.emit(this.checkeditem);
    }

    togleCheckedAll(): void {
        this.isCheckedAll = !this.isCheckedAll;
        this.checkedAll.emit(this.isCheckedAll);

        if (!this.isCheckedAll) {
            this.makenotCheckall();
        } else {
            this.makeCheckAll();
        }

        this.outputCheckItem.emit(this.checkeditem);
    }
}
