import { filter } from 'rxjs/operators';
import { IOurPagination } from './../../shared/our-pagination/pagination.model';
import { HttpResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { EventService } from 'app/entities/event/service/event.service';
import { StoreService } from 'app/entities/store/service/store.service';
import { VoucherImageService } from 'app/entities/voucher-image/service/voucher-image.service';
import { IVoucherImage } from 'app/entities/voucher-image/voucher-image.model';
import { VoucherService } from 'app/entities/voucher/service/voucher.service';
import { IVoucher } from 'app/entities/voucher/voucher.model';
import { EntityResponseType } from './../../entities/voucher-status/service/voucher-status.service';
import { PriceRange, IMyFilter, MyFilter } from './model';
import { ActivatedRoute, Params } from '@angular/router';

@Component({
    selector: 'jhi-sell-voucher',
    templateUrl: './sell-voucher.component.html',
    styleUrls: ['./sell-voucher.component.scss'],
})
export class SellVoucherComponent implements OnInit {
    vouchers!: IVoucher[];
    backupVouchers!: IVoucher[];
    pagination?: IOurPagination;

    isVoucherLoading = false;

    typeId?: number;

    provinceIds = new Array(0);

    ratings = new Array(0);

    priceRange?: PriceRange = new PriceRange(0, 1000000);

    search?: string | null;

    myFilter?: IMyFilter = {
        page: 1,
        limit: 6,
        type: 0,
        sort: '',
        search: '',
    };

    constructor(
        private voucherService: VoucherService,
        private voucherImageService: VoucherImageService,
        private eventService: EventService,
        private storeService: StoreService,
        private route: ActivatedRoute
    ) {}

    ngOnInit(): void {
        this.route.paramMap.subscribe(params => {
            if (params.get('search')) {
                if (this.myFilter?.search != null) {
                    this.myFilter.search = params.get('search');
                }
                this.loadWithPaging();
            }
        });

        this.loadWithPaging();
    }

    setValueDefault(): void {
        this.myFilter = {
            page: 1,
            limit: 6,
            type: 0,
            sort: '',
            search: '',
        };
    }

    loadWithPaging(): void {
        this.voucherService.findWithPaging(this.myFilter!).subscribe(
            res => {
                window.console.log('Duong first filter ', this.myFilter);
                this.pagination = res.body ?? [];
                this.vouchers = this.pagination?.items ?? [];
                window.console.log('Duong pagiantion: ', this.pagination);
            },
            () => window.console.log('An error occurs when loading voucher data!')
        );
    }

    loadVoucherByTypeId(): void {
        this.vouchers = this.backupVouchers.filter((voucher: IVoucher) => voucher.type?.id === this.typeId);
    }

    loadVoucherByPriceRange(): void {
        this.vouchers = this.vouchers.filter(
            (voucher: IVoucher) =>
                voucher.price! >= (this.priceRange?.minPrice ?? 0) * 1000 &&
                voucher.price! <= (this.priceRange?.maxPrice ?? 10000000) * 1000
        );
    }

    loadVoucherByMutipleCriteria(): void {
        if (typeof this.typeId === 'number') {
            this.loadVoucherByTypeId();
        }
        this.loadVoucherByPriceRange();
    }

    pageChangedHandler(page: number): void {
        this.myFilter?.page ? (this.myFilter.page = page) : 1;
        this.loadWithPaging();
    }

    serviceTypeChangedHandler(id: number): void {
        if (this.myFilter?.type != null) {
            this.myFilter.type = id;
        }
        this.loadWithPaging();
    }

    areaChangedHandler(ids: Array<number>): void {
        this.provinceIds = [];
        ids.forEach(id => this.provinceIds.push(id));
    }

    priceRangeChangedHandler(data: PriceRange): void {
        this.priceRange = new PriceRange(data.minPrice, data.maxPrice);
        this.loadVoucherByMutipleCriteria();
    }

    ratingChangedHandler(items: Array<number>): void {
        this.ratings = [];
        items.forEach(value => this.ratings.push(value));
    }

    optionSelectedChangedHandler(value: string): void {
        if (this.myFilter?.sort != null) {
            this.myFilter.sort = value;
        }
        this.loadWithPaging();
    }
}
