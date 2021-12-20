import { Component, Input, OnInit, ViewChild } from '@angular/core';
import { NgbCarousel, NgbSlideEvent, NgbSlideEventSource } from '@ng-bootstrap/ng-bootstrap';
import { IVoucherImage } from 'app/entities/voucher-image/voucher-image.model';

@Component({
    selector: 'jhi-slide-detail',
    templateUrl: './slide-detail.component.html',
    styleUrls: ['./slide-detail.component.scss'],
})
export class SlideDetailComponent implements OnInit {
    @Input() voucherImages?: IVoucherImage[];

    paused = false;
    unpauseOnArrow = false;
    pauseOnIndicator = false;
    pauseOnHover = true;
    pauseOnFocus = true;

    @ViewChild('carousel', { static: true }) carousel: NgbCarousel | undefined;

    // constructor() {
    // }

    ngOnInit(): void {
        return;
    }

    togglePaused(): void {
        if (this.paused) {
            this.carousel?.cycle();
        } else {
            this.carousel?.pause();
        }
        this.paused = !this.paused;
    }

    onSlide(slideEvent: NgbSlideEvent): void {
        if (
            this.unpauseOnArrow &&
            slideEvent.paused &&
            (slideEvent.source === NgbSlideEventSource.ARROW_LEFT || slideEvent.source === NgbSlideEventSource.ARROW_RIGHT)
        ) {
            this.togglePaused();
        }
        if (this.pauseOnIndicator && !slideEvent.paused && slideEvent.source === NgbSlideEventSource.INDICATOR) {
            this.togglePaused();
        }
    }
}
