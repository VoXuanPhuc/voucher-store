import { Component, HostListener, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { LoginService } from 'app/login/login.service';

@Component({
  selector: 'jhi-my-header',
  templateUrl: './my-header.component.html',
  styleUrls: ['./my-header.component.css'],
})
export class MyHeaderComponent implements OnInit {
  isDisplayLogin = false;
  isDisplayCategory = false;
  isHeaderFix = true;
  lastScrollTop = 0;
  jwtSession = '';

  constructor(private router: Router, private loginService: LoginService) {}

  ngOnInit(): void {
    const jwt = sessionStorage.getItem('jhi-authenticationToken');
    this.jwtSession = jwt !== null ? jwt : '';
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
    this.router.navigate(['']);
    window.location.reload();
  }
}
