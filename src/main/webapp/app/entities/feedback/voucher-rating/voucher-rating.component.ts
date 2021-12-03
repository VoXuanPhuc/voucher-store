import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { FormArray, FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'jhi-voucher-rating',
  templateUrl: './voucher-rating.component.html',
  styleUrls: ['./voucher-rating.component.scss'],
})
export class VoucherRatingComponent {
  form?: FormGroup;

  @Output() checkboxChanged: EventEmitter<Array<number>> = new EventEmitter();

  constructor(private formBuilder: FormBuilder) {
    this.form = this.formBuilder.group({
      rating: this.formBuilder.array([], [Validators.required]),
    });
  }

  onCheckboxChange(event: any): void {
    const rating: FormArray = this.form?.get('rating') as FormArray;

    if (event.target.checked) {
      rating.push(new FormControl(event.target.value));
    } else {
      const index = rating.controls.findIndex(x => x.value === event.target.value);
      rating.removeAt(index);
    }

    const ratings = new Array(0);

    for (let i = 0; i < rating.length; i++) {
      ratings.push(rating.at(i).value);
    }

    this.checkboxChanged.emit(ratings);
  }
}
