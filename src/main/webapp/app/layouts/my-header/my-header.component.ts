import { Component, HostListener, OnInit } from '@angular/core';

@Component({
  selector: 'jhi-my-header',
  templateUrl: './my-header.component.html',
  styleUrls: ['./my-header.component.css'],
})
export class MyHeaderComponent {
  isDisplayLogin = false;
  isDisplayCategory = false;
  isHeaderFix = true;
  lastScrollTop = 0;

  toggleDisplayLogin(): void {
    this.isDisplayLogin = !this.isDisplayLogin;
  }

  toggleDisplayCategory(): void {
    this.isDisplayCategory = !this.isDisplayCategory;
  }

  @HostListener('window:scroll', ['$event']) onScroll(): void {
    const doc = document.documentElement;
    const w = window;
    const srcollTop = w.pageYOffset || doc.scrollTop;

    if (srcollTop > this.lastScrollTop) {
      this.isHeaderFix = false;
    } else {
      this.isHeaderFix = true;
    }
    this.lastScrollTop = srcollTop;
  }
}
