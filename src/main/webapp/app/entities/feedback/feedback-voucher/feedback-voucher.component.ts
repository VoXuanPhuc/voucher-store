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

  constructor(private feedbackService: FeedbackService, private myuserService: MyUserService, private activatedRoute: ActivatedRoute) {
    this.pageNumbers = 1;
    this.currentPage = 0;
    this.feedBackNumber = 0;
    this.pages = [];
    this.idVoucher = 1;
  }

  ngOnInit(): void {
    this.activatedRoute.params.subscribe(param => {
      this.idVoucher = param['id'];
    });
    this.loadFeedBackByVoucher();
    this.countFeedBackByVoucher();
  }

  // paging of feedback
  setPage(i: number, event: any): void {
    event.preventDefault();
    this.currentPage = i;
    this.loadFeedBackByVoucher();
  }

  nextPage(event: any): void {
    event.preventDefault();
    this.currentPage = this.currentPage + 1;
    this.loadFeedBackByVoucher();
  }

  previousPage(event: any): void {
    event.preventDefault();
    this.currentPage = this.currentPage - 1;
    this.loadFeedBackByVoucher();
  }

  //load feedback by voucher id
  loadFeedBackByVoucher(): void {
    this.feedbackService.getFeedbacksByVoucher(this.idVoucher, this.currentPage).subscribe(
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
      if (this.feedBackNumber % 6 !== 0) {
        this.pageNumbers = this.feedBackNumber / 6 + 1;
      } else {
        this.pageNumbers = this.feedBackNumber / 6;
      }
      this.pages = new Array(this.pageNumbers);
    });
  }

  trackId(index: number, item: IFeedback): number {
    return item.id!;
  }

  checkStart(start: number, rate: any): boolean {
    return start > rate;
  }
}
