import { Component, OnInit } from '@angular/core';

import { Feature } from './feature';


@Component({
  selector: 'app-feature',
  templateUrl: './features.component.html',
  styleUrls: ['./features.component.scss']
})
export class FeaturesComponent implements OnInit {
  features: Feature[];

  constructor() { }

  ngOnInit() {
    this.features =
    [
      {
        name: 'Play Quiz',
        desc: 'Unique Feature that allows a user to play a quiz that he desires to.',
        order: 1
      },
      {
        name: 'Create Quiz',
        desc: 'A Feature that allows a user to author a quiz according to a domain that he wants to.',
        order: 2
      },
      {
        name: 'Challenge/Invite Quiz',
        desc: 'Allows users to challenge/invite their followers for a quiz ',
        order: 3
      },
      {
        name: 'Create Question',
        desc: 'Lets A User to create any kind of question, either a mcq or descriptive or code snippet',
        order: 4
      },
      {
        name: 'Leaderboard',
        desc: 'Points and rank a person holds among the crowd',
        order: 5
      }
    ];
  }

}
