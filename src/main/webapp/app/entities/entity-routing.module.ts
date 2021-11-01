import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'province',
        data: { pageTitle: 'Provinces' },
        loadChildren: () => import('./province/province.module').then(m => m.ProvinceModule),
      },
      {
        path: 'district',
        data: { pageTitle: 'Districts' },
        loadChildren: () => import('./district/district.module').then(m => m.DistrictModule),
      },
      {
        path: 'village',
        data: { pageTitle: 'Villages' },
        loadChildren: () => import('./village/village.module').then(m => m.VillageModule),
      },
      {
        path: 'address',
        data: { pageTitle: 'Addresses' },
        loadChildren: () => import('./address/address.module').then(m => m.AddressModule),
      },
      {
        path: 'role',
        data: { pageTitle: 'Roles' },
        loadChildren: () => import('./role/role.module').then(m => m.RoleModule),
      },
      {
        path: 'my-user',
        data: { pageTitle: 'MyUsers' },
        loadChildren: () => import('./my-user/my-user.module').then(m => m.MyUserModule),
      },
      {
        path: 'store',
        data: { pageTitle: 'Stores' },
        loadChildren: () => import('./store/store.module').then(m => m.StoreModule),
      },
      {
        path: 'relationship-type',
        data: { pageTitle: 'RelationshipTypes' },
        loadChildren: () => import('./relationship-type/relationship-type.module').then(m => m.RelationshipTypeModule),
      },
      {
        path: 'store-user',
        data: { pageTitle: 'StoreUsers' },
        loadChildren: () => import('./store-user/store-user.module').then(m => m.StoreUserModule),
      },
      {
        path: 'benifit-package',
        data: { pageTitle: 'BenifitPackages' },
        loadChildren: () => import('./benifit-package/benifit-package.module').then(m => m.BenifitPackageModule),
      },
      {
        path: 'category',
        data: { pageTitle: 'Categories' },
        loadChildren: () => import('./category/category.module').then(m => m.CategoryModule),
      },
      {
        path: 'product',
        data: { pageTitle: 'Products' },
        loadChildren: () => import('./product/product.module').then(m => m.ProductModule),
      },
      {
        path: 'event',
        data: { pageTitle: 'Events' },
        loadChildren: () => import('./event/event.module').then(m => m.EventModule),
      },
      {
        path: 'service-type',
        data: { pageTitle: 'ServiceTypes' },
        loadChildren: () => import('./service-type/service-type.module').then(m => m.ServiceTypeModule),
      },
      {
        path: 'voucher-image',
        data: { pageTitle: 'VoucherImages' },
        loadChildren: () => import('./voucher-image/voucher-image.module').then(m => m.VoucherImageModule),
      },
      {
        path: 'voucher',
        data: { pageTitle: 'Vouchers' },
        loadChildren: () => import('./voucher/voucher.module').then(m => m.VoucherModule),
      },
      {
        path: 'voucher-code',
        data: { pageTitle: 'VoucherCodes' },
        loadChildren: () => import('./voucher-code/voucher-code.module').then(m => m.VoucherCodeModule),
      },
      {
        path: 'voucher-status',
        data: { pageTitle: 'VoucherStatuses' },
        loadChildren: () => import('./voucher-status/voucher-status.module').then(m => m.VoucherStatusModule),
      },
      {
        path: 'order',
        data: { pageTitle: 'Orders' },
        loadChildren: () => import('./order/order.module').then(m => m.OrderModule),
      },
      {
        path: 'order-status',
        data: { pageTitle: 'OrderStatuses' },
        loadChildren: () => import('./order-status/order-status.module').then(m => m.OrderStatusModule),
      },
      {
        path: 'feedback',
        data: { pageTitle: 'Feedbacks' },
        loadChildren: () => import('./feedback/feedback.module').then(m => m.FeedbackModule),
      },
      {
        path: 'feedback-image',
        data: { pageTitle: 'FeedbackImages' },
        loadChildren: () => import('./feedback-image/feedback-image.module').then(m => m.FeedbackImageModule),
      },
      {
        path: 'gift',
        data: { pageTitle: 'Gifts' },
        loadChildren: () => import('./gift/gift.module').then(m => m.GiftModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
