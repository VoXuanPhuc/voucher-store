import { Component, OnInit } from '@angular/core';
import { CartService } from 'app/entities/my-cart/cart.service';
import { ICartVoucher } from 'app/entities/my-cart/CartVoucher.model';

@Component({
    selector: 'jhi-cart-page',
    templateUrl: './cart-page.component.html',
    styleUrls: ['./cart-page.component.scss'],
})
export class CartPageComponent implements OnInit {
    cartItem: ICartVoucher[];
    // isCheckAll: boolean;
    isCheckitem: boolean[];
    constructor(private cartService: CartService) {
        this.isCheckitem = [];
        this.cartItem = [];
        // this.isCheckAll = true;
    }

    ngOnInit(): void {
        this.loadVoucherInCart();
    }

    loadVoucherInCart(): void {
        this.cartService.loadCart();
        this.cartItem = this.cartService.getItems();
    }

    // isCheckedAllAgain(isCheckall: any): void {
    //     this.isCheckAll = isCheckall;
    // }

    isChangeCheckitem(isCheckitem: any): void {
        this.isCheckitem = [];
        this.isCheckitem = [...isCheckitem];
    }
}
