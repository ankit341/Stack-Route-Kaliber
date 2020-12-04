// import { async, ComponentFixture, TestBed, inject } from '@angular/core/testing';

// import { PreviewComponent } from './preview.component';
// import { MatDialogModule, MatDialogRef, MatDialog } from '@angular/material';
// import { KaliberMaterialModule } from 'src/app/kaliber-material.module';
// import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
// import { HttpClient } from 'selenium-webdriver/http';
// import { HttpClientModule } from '@angular/common/http';
// import { OverlayContainer } from '@angular/cdk/overlay';

// describe('PreviewComponent', () => {
//     let dialog: MatDialog;
//     let overlayContainer: OverlayContainer;
//     // tslint:disable-next-line: prefer-const
//     let component: PreviewComponent;
//     // tslint:disable-next-line: prefer-const
//     let fixture: ComponentFixture<PreviewComponent>;
//     beforeEach(async(() => {
//         TestBed.configureTestingModule({
//             declarations: [PreviewComponent],
//             imports: [
//                 MatDialogModule, KaliberMaterialModule, BrowserAnimationsModule, HttpClientModule],
//             providers: [{ provide: MatDialogRef }]
//         });
//         TestBed.overrideModule(BrowserAnimationsModule, {
//             set: {
//                 entryComponents: [PreviewComponent]
//             }
//         });
//         TestBed.compileComponents();
//     }));
//     beforeEach(inject([MatDialog, OverlayContainer],
//         (d: MatDialog, oc: OverlayContainer) => {
//             dialog = d;
//             overlayContainer = oc;
//         })
//     );
//     afterEach(() => {
//         overlayContainer.ngOnDestroy();
//     });
//     it('should open a dialog ', () => {
//         const dialogRef = dialog.open(PreviewComponent, {
//             width: '300px', height: '200px'
//         });
//         // verify
//         expect(dialogRef.componentInstance instanceof PreviewComponent).toBe(true);
//     });
// });








