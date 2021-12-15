import { AfterViewChecked, Component, HostListener, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { CartService } from 'app/entities/my-cart/cart.service';
import { LoginService } from 'app/login/login.service';

@Component({
    selector: 'jhi-my-header',
    templateUrl: './my-header.component.html',
    styleUrls: ['./my-header.component.css'],
})
export class MyHeaderComponent implements OnInit, AfterViewChecked {
    isDisplayLogin = false;
    isDisplayCategory = false;
    isHeaderFix = true;
    lastScrollTop = 0;
    jwtSession = '';
    keyword?: string | null;

    itemInCart: number;

    constructor(private router: Router, private loginService: LoginService, private cartService: CartService) {
        this.itemInCart = 0;
        this.keyword = null;
    }

    ngOnInit(): void {
        const jwt = sessionStorage.getItem('jhi-authenticationToken');
        this.jwtSession = jwt !== null ? jwt : '';

        this.cartService.totalItemChange.subscribe(item => {
            window.console.log(item);
            this.itemInCart = this.cartService.countTotalInCart();
        });
    }

    ngAfterViewChecked(): void {
        this.itemInCart = this.cartService.countTotalInCart();
    }

    isLogin(): boolean {
        return this.jwtSession !== '';
    }

    toggleDisplayLogin(): void {
        this.isDisplayLogin = !this.isDisplayLogin;
        this.isDisplayCategory = false;
    }

    toggleDisplayCategory(): void {
        this.isDisplayCategory = !this.isDisplayCategory;
        this.isDisplayLogin = false;
    }

    @HostListener('window:scroll', ['$event']) onScroll(): void {
        const doc = document.documentElement;
        const w = window;
        const srcollTop = w.pageYOffset || doc.scrollTop;

        if (srcollTop > this.lastScrollTop) {
            this.isHeaderFix = false;
            this.isDisplayCategory = false;
            this.isDisplayLogin = false;
        } else {
            this.isHeaderFix = true;
        }
        this.lastScrollTop = srcollTop;
    }

    logout(): void {
        this.loginService.logout();
        location.replace('/login');
        // this.router.navigate(['/login']);
    }

    onSearch(key: string): void {
        if (!key) {
            return;
        }
        this.keyword = key;
        this.router.navigate(['/vouchers', key], { skipLocationChange: true });
    }
}
