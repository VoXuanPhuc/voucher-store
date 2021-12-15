import { Component, OnInit, Output, EventEmitter, Input } from '@angular/core';

@Component({
    selector: 'jhi-voucher-sort',
    templateUrl: './voucher-sort.component.html',
    styleUrls: ['./voucher-sort.component.scss'],
})
export class VoucherSortComponent implements OnInit {
    public PRICE_ASC = 'price.asc';
    public PRICE_DESC = 'price.desc';
    public NAME_ASC = 'name.asc';
    public NAME_DESC = 'name.desc';

    @Input() keyword?: string | null;

    @Output() optionSelectedChanged = new EventEmitter();

    constructor() {
        return;
    }

    ngOnInit(): void {
        return;
    }

    onOptionSelectedChanged(value: string): void {
        let sort = '';

        switch (value) {
            case '0':
                sort = '';
                break;
            case '1':
                sort = this.PRICE_ASC;
                break;
            case '2':
                sort = this.PRICE_DESC;
                break;
            case '3':
                sort = this.NAME_ASC;
                break;
            case '4':
                sort = this.NAME_DESC;
                break;

            default:
                break;
        }

        this.optionSelectedChanged.emit(sort);
    }
}
