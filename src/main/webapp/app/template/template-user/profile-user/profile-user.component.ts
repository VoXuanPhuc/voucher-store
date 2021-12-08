import { Component, OnInit } from '@angular/core';
import { IAddress } from 'app/entities/address/address.model';
import { AddressService } from 'app/entities/address/service/address.service';
import { IDistrict } from 'app/entities/district/district.model';
import { DistrictService } from 'app/entities/district/service/district.service';
import { IMyUser } from 'app/entities/my-user/my-user.model';
import { MyUserService } from 'app/entities/my-user/service/my-user.service';
import { ProvinceService } from 'app/entities/province/service/province.service';
import { VillageService } from 'app/entities/village/service/village.service';
import { IVillage } from 'app/entities/village/village.model';

@Component({
  selector: 'jhi-profile-user',
  templateUrl: './profile-user.component.html',
  styleUrls: ['./profile-user.component.scss'],
})
export class ProfileUserComponent implements OnInit {
  myUser: IMyUser | null = null;
  constructor(
    private myUserService: MyUserService,
    private addressService: AddressService,
    private villageService: VillageService,
    private districtService: DistrictService,
    private provinceService: ProvinceService
  ) {}

  ngOnInit(): void {
    this.loadUserLogin();
  }

  loadUserLogin(): void {
    this.myUserService.getUserByJWT().subscribe(
      myUser => {
        this.myUser = myUser.body;
        this.addressService.find(this.myUser?.address?.id ?? 0).subscribe(address => {
          this.myUser!.address = address.body;
          this.loadVillage(this.myUser!.address!);
        });
      },
      () => {
        window.alert('Ban Chua login');
      }
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
      },
      error => window.console.log('Error: ', error)
    );
  }
}
