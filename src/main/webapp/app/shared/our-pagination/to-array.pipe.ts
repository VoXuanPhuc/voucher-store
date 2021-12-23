import { Pipe, PipeTransform } from '@angular/core';

@Pipe({ name: 'toArray' })
export class ToArray implements PipeTransform {
  transform(value: number): any {
    const res = [];
    for (let i = 0; i < value; i++) {
      res.push(i);
    }
    return res;
  }
}
