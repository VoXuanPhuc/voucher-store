import { Pipe, PipeTransform } from '@angular/core';
import * as dayjs from 'dayjs';
import { Dayjs } from 'dayjs';

@Pipe({
    name: 'dateFormat',
})
export class DateFormatPipe implements PipeTransform {
    transform(value: Dayjs | undefined): string {
        if (value !== undefined) {
            return dayjs(value).format('DD-MM-YYYY');
        }
        return '01/01/2001';
    }
}
