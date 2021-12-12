import { Injectable } from '@angular/core';
import { ICartVoucher } from './cartVoucher.model';

@Injectable({
    providedIn: 'root',
})
export class CartService {
    items: ICartVoucher[];

    constructor() {
        this.items = [];
    }

    addToCart(cartVoucher: ICartVoucher): void {
        this.items.push(cartVoucher);
    }

    getItems(): ICartVoucher[] {
        return this.items;
    }

    loadCart(): void {
        this.items = JSON.parse(localStorage.getItem('cart_items')!);
    }

    saveCart(): void {
        localStorage.setItem('cart_items', JSON.stringify(this.items));
    }

    checkItemInCart(cartVoucher: ICartVoucher): boolean {
        return this.items.findIndex(item => item.voucher?.id === cartVoucher.voucher?.id) > -1;
    }

    getItemInCart(cartVoucher: ICartVoucher): ICartVoucher {
        return this.items.find(item => item.voucher?.id === cartVoucher.voucher?.id)!;
    }
}
