import { Component, OnInit } from '@angular/core';
import {
  MatDialog,
  MatDialogRef,
  MAT_DIALOG_DATA
} from '@angular/material/dialog';
import { Papa } from 'ngx-papaparse';
import { LoggerService } from 'src/app/services/logger.service';

@Component({
  selector: 'app-import-question-dialog',
  templateUrl: './import-question-dialog.component.html',
  styleUrls: ['./import-question-dialog.component.scss']
})
export class ImportQuestionDialogComponent implements OnInit {
  test = [];
  constructor(
    public dialogRef: MatDialogRef<ImportQuestionDialogComponent>,
    private papa: Papa,
    private logger: LoggerService
  ) {}

  ngOnInit() {}

  onNoClick(): void {
    this.dialogRef.close();
  }

  handleFileSelect(evt) {
    const files = evt.target.files; // FileList object
    const file = files[0];
    const reader = new FileReader();
    reader.readAsText(file);
    reader.onload = (event: any) => {
      const csv = event.target.result; // Content of CSV file
      this.papa.parse(csv, {
        skipEmptyLines: true,
        header: true,
        complete: results => {
          // tslint:disable-next-line: prefer-for-of
          for (let i = 0; i < results.data.length; i++) {
            const orderDetails = {
              code: results.data[i].Code,
              amenities: results.data[i].Access_to_basic_amenities,
              Occupied_private_dwellings:
                results.data[i].Occupied_private_dwellings
            };
            this.logger.log(orderDetails);
            this.test.push(orderDetails);
          }
        }
      });
    };
  }
}
