import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { ICartVoucher } from './CartVoucher.model';

@Injectable({
    providedIn: 'root',
})
export class CartService {
    items: ICartVoucher[];
    detectedTotalItemChange = new BehaviorSubject(1);
    totalItemChange = this.detectedTotalItemChange.asObservable();
    constructor() {
        this.items = [];
    }

    changeItem(itemChange: number): void {
        this.detectedTotalItemChange.next(itemChange);
    }

    addToCart(cartVoucher: ICartVoucher): void {
        this.items.push(cartVoucher);
    }

    getItems(): ICartVoucher[] {
        return this.items;
    }

    deleteItem(cartVoucher: ICartVoucher): void {
        this.items = this.items.filter(item => item !== cartVoucher);
        this.saveCart();
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

    countTotalInCart(): number {
        let total = 0;
        this.items.forEach(item => {
            total += item.total!;
        });

        return total;
    }
}
