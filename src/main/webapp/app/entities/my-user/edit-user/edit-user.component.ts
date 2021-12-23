import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { IAddress } from 'app/entities/address/address.model';
import { IDistrict } from 'app/entities/district/district.model';
import { IVillage } from 'app/entities/village/village.model';
import * as dayjs from 'dayjs';
import { AddressService } from './../../address/service/address.service';
import { DistrictService } from './../../district/service/district.service';
import { IProvince } from './../../province/province.model';
import { ProvinceService } from './../../province/service/province.service';
import { VillageService } from './../../village/service/village.service';
import { IMyUser } from './../my-user.model';
import { MyUserService } from './../service/my-user.service';

@Component({
    selector: 'jhi-edit-user',
    templateUrl: './edit-user.component.html',
    styleUrls: ['./edit-user.component.scss'],
})
export class EditUserComponent implements OnInit {
    user?: IMyUser | null;
    provinces?: IProvince[];
    districts?: IDistrict[];
    villages?: IVillage[];

    editForm = this.formBuilder.group({
        firstName: [null, [Validators.required]],
        lastName: [null, [Validators.required]],
        email: [null, [Validators.required]],
        phone: [null, [Validators.required]],
        dob: [null, [Validators.required]],
        gender: [null, [Validators.required]],
        province: [null, [Validators.required]],
        district: [null, [Validators.required]],
        village: [null, [Validators.required]],
        address: [null, [Validators.required]],
    });

    constructor(
        private formBuilder: FormBuilder,
        private addressService: AddressService,
        private villageService: VillageService,
        private districtService: DistrictService,
        private provinceService: ProvinceService,
        private myUserService: MyUserService,
        private route: Router
    ) {
        return;
    }

    ngOnInit(): void {
        this.getAllProvince();
        this.getAllDistrict();
        this.getAllVillage();

        this.loadCurrentUser();
    }

    updateForm(myUser: IMyUser): void {
        window.console.log('1Duong userrrrrrrrr: ', myUser.address?.village);

        this.editForm.patchValue({
            id: myUser.id,
            username: myUser.username,
            password: myUser.password,
            firstName: myUser.firstName,
            lastName: myUser.lastName,
            gender: myUser.gender,
            phone: myUser.phone,
            email: myUser.email,
            dob: dayjs(myUser.dob).format('YYYY-MM-DD'),
            address: myUser.address?.street,
            province: myUser.address?.village?.district?.province?.id,
            district: myUser.address?.village?.district?.id,
            village: myUser.address?.village?.id,
        });
    }

    loadCurrentUser(): void {
        this.myUserService.getUserByJWT().subscribe(
            res => {
                this.user = res.body;
                this.addressService.find(this.user?.address?.id ?? 0).subscribe(address => {
                    this.user!.address = address.body;
                    this.loadVillage(this.user!.address!);
                });
            },
            () => {
                window.alert('Please login !!!');
            }
        );
    }

    save(): void {
        const userUpdate = this.createUserFromForm();
        window.console.log('user updateeeeeee: ', userUpdate);
        if (userUpdate.id !== undefined) {
            this.myUserService.partialUpdate(userUpdate).subscribe(
                res => {
                    window.console.log('Save successfully');
                    this.route.navigate(['/profile']);
                },
                error => window.console.log('Error: ', error)
            );
        }
    }

    createUserFromForm(): IMyUser {
        return {
            ...this.user,
            firstName: this.editForm.get(['firstName'])!.value,
            lastName: this.editForm.get(['lastName'])!.value,
            gender: this.editForm.get(['gender'])!.value,
            phone: this.editForm.get(['phone'])!.value,
            email: this.editForm.get(['email'])!.value,
            dob: this.editForm.get(['dob'])!.value,
        };
    }

    getAllProvince(): void {
        this.provinceService.query().subscribe(
            res => {
                this.provinces = res.body ?? [];
            },
            error => window.console.log(error)
        );
    }

    getAllDistrict(): void {
        this.districtService.query().subscribe(
            res => (this.districts = res.body ?? []),
            error => window.console.log(error)
        );
    }

    getAllVillage(): void {
        this.villageService.query().subscribe(
            res => (this.villages = res.body ?? []),
            error => window.console.log(error)
        );
    }

    loadVillage(address: IAddress): void {
        this.villageService.find(address.village?.id ?? 1).subscribe(
            res => {
                address.village = res.body;

                if (address.village) {
                    this.loadDistrict(address.village);
                }
            },
            error => window.console.log('Error: ', error)
        );
    }

    loadDistrict(village: IVillage): void {
        this.districtService.find(village.district?.id ?? 1).subscribe(
            res => {
                village.district = res.body;

                if (village.district) {
                    this.loadProvince(village.district);
                }
            },
            error => window.console.log('Error: ', error)
        );
    }

    loadProvince(district: IDistrict): void {
        this.provinceService.find(district.province?.id ?? 1).subscribe(
            res => {
                district.province = res.body;
                this.updateForm(this.user!);
            },
            error => window.console.log('Error: ', error)
        );
    }
}
