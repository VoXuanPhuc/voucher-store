import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { MyUserService } from 'app/entities/my-user/service/my-user.service';
import { IFeedback } from '../feedback.model';
import { FeedbackService } from '../service/feedback.service';

@Component({
  selector: 'jhi-feedback-voucher',
  templateUrl: './feedback-voucher.component.html',
  styleUrls: ['./feedback-voucher.component.scss'],
})
export class FeedbackVoucherComponent implements OnInit {
  currentPage: number;
  feedbacks?: IFeedback[];
  pages: Array<number>;
  pageNumbers: number;
  feedBackNumber: any;
  idVoucher: number;
  stateStar: boolean;
  star: number;
  constructor(private feedbackService: FeedbackService, private myuserService: MyUserService, private activatedRoute: ActivatedRoute) {
    this.pageNumbers = 0;
    this.currentPage = 0;
    this.feedBackNumber = 0;
    this.pages = [];
    this.idVoucher = 1;
    this.stateStar = false;
    this.star = 0;
  }

  ngOnInit(): void {
    this.activatedRoute.params.subscribe(param => {
      this.idVoucher = param['id'];
    });
    this.loadFeedBackByVoucher();
  }

  // paging of feedback
  setPage(i: number, event: any): void {
    event.preventDefault();
    this.currentPage = i;
    if (this.stateStar) {
      this.loadFeedBackByVoucherAndRate(this.star);
      return;
    }
    this.loadFeedBackByVoucher();
  }

  nextPage(event: any): void {
    event.preventDefault();
    if (this.currentPage >= this.pages.length - 1) {
      return;
    }
    this.currentPage = this.currentPage + 1;
    if (this.stateStar) {
      this.loadFeedBackByVoucherAndRate(this.star);
      return;
    }
    this.loadFeedBackByVoucher();
  }

  previousPage(event: any): void {
    event.preventDefault();
    if (this.currentPage === 0) {
      return;
    }
    this.currentPage = this.currentPage - 1;
    if (this.stateStar) {
      this.loadFeedBackByVoucherAndRate(this.star);
      return;
    }
    this.loadFeedBackByVoucher();
  }

  //load feedback by voucher id
  loadFeedBackByVoucher(): void {
    this.stateStar = false;
    this.feedbackService.getFeedbacksByVoucher(this.idVoucher, this.currentPage).subscribe(
      data => {
        this.feedbacks = data.body ?? [];
        this.countFeedBackByVoucher();
        this.feedbacks.forEach(feedback => {
          this.myuserService.find(feedback.user?.id ?? 1).subscribe(user => {
            feedback.user = user.body;
          });
        });
      },
      error => {
        window.console.log(error);
      }
    );
  }

  // load feedback by voucher and rate
  loadFeedBackByVoucherAndRate(rate: number): void {
    //set page = 0
    this.star = rate;
    this.stateStar = true;
    this.countFeedBackByVoucherAndRate(rate);
    this.feedbackService.getFeedbacksByVoucherAndRate(this.idVoucher, rate, this.currentPage).subscribe(
      data => {
        this.feedbacks = data.body ?? [];
        this.feedbacks.forEach(feedback => {
          this.myuserService.find(feedback.user?.id ?? 1).subscribe(user => {
            feedback.user = user.body;
          });
        });
      },
      error => {
        window.console.log(error);
      }
    );
  }

  // call api count total feedback of voucherid
  countFeedBackByVoucher(): void {
    this.feedbackService.countVoucherCode(this.idVoucher).subscribe(data => {
      this.feedBackNumber = data.body;
      this.countPageFeedback(this.feedBackNumber);
    });
  }

  // call api count total feedback of voucherid and Rate
  countFeedBackByVoucherAndRate(rate: number): void {
    this.feedbackService.countVoucherCodeAndRate(this.idVoucher, rate).subscribe(data => {
      this.feedBackNumber = data.body;
      this.countPageFeedback(this.feedBackNumber);
    });
  }

  // count total page of feedback
  countPageFeedback(feedbackNumber: number): void {
    if (this.feedBackNumber % 6 !== 0) {
      this.pageNumbers = this.feedBackNumber / 6;
    } else {
      this.pageNumbers = this.feedBackNumber / 6;
    }
    this.pageNumbers = Math.floor(this.pageNumbers);
    this.pages = new Array(this.pageNumbers + 1);
  }

  trackId(index: number, item: IFeedback): number {
    return item.id!;
  }

  checkStart(start: number, rate: any): boolean {
    return start > rate;
  }
}
