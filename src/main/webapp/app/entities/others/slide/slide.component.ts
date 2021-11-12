import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'jhi-slide',
  templateUrl: './slide.component.html',
  styleUrls: ['./slide.component.scss'],
})
export class SlideComponent implements OnInit {
  images = [944, 1011, 984].map(n => `https://picsum.photos/id/${n}/1200/347`);

  // constructor() { }

  ngOnInit(): void {
    return;
  }
}
