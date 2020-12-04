import { COMMA, ENTER } from '@angular/cdk/keycodes';
import { Component, ElementRef, ViewChild, Input } from '@angular/core';
import { FormControl } from '@angular/forms';
import {
  MatAutocompleteSelectedEvent,
  MatAutocomplete
} from '@angular/material/autocomplete';
import { MatChipInputEvent } from '@angular/material/chips';
import { Observable } from 'rxjs';
import { map, startWith } from 'rxjs/operators';
import { UserService } from 'src/app/services/user.service';
import { LoggerService } from 'src/app/services/logger.service';


/**
 * @title Chips Autocomplete
 */
@Component({
  selector: 'app-chip',
  templateUrl: './chip.component.html',
  styleUrls: ['./chip.component.css']
})
export class ChipComponent {
  @Input() topicsList: any;
  visible = true;
  selectable = true;
  removable = true;
  addOnBlur = true;
  readonly separatorKeysCodes: number[] = [ENTER, COMMA];
  fruitCtrl = new FormControl();
  filteredFruits: Observable<string[]>;
  fruits: any[];
  userName = 'def@gmail.com';
  topics = ['Docker'] ;
  userDetailsData: any;
  public userDetais: any;
  @Input() allFruits: string[];

  @ViewChild('fruitInput', { static: false }) fruitInput: ElementRef<
    HTMLInputElement
  >;
  @ViewChild('auto', { static: false }) matAutocomplete: MatAutocomplete;

  constructor(private userService: UserService,
              private logger: LoggerService) {

  }


  toAddNewTopic(chip) {
    this.logger.log('cjhip=-------> ', chip);
    this.topicsList.push(this.topics);
    this.userService.addNewTopic(this.userName, this.topics).subscribe(data => {
      this.userDetailsData = data;
      this.userDetais = this.userDetailsData.result;
      this.logger.log(this.userDetais);
      this.logger.log('Adding topics');
    });
  }




  // toAddNewTopic() {
  //   this.userService.getByUserName(this.userName).subscribe(data => {
  //     this.userDetailsData = data;
  //     this.userDetails = this.userDetailsData.result;
  //     this.logger.log(this.userDetails);
  //   });
  // }

}
