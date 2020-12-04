import { Component, OnInit} from '@angular/core';
import { TemplateSet } from './template-set';

@Component({
  selector: 'app-gallery',
  templateUrl: './gallery.component.html',
  styleUrls: ['./gallery.component.scss']
})
export class GalleryComponent implements OnInit {
  templateSets: TemplateSet[];


  constructor() { }

  ngOnInit() {

    this.templateSets =
      [
        {
          name: 'Java',
          description: 'Angular Material Design Template-Set',
          imgUrl: 'assets/images/java.png',
          order: 1,
        },
        {
          name: 'SpringBoot',
          description: 'Angular Bootstrap Template-Set',
          imgUrl: 'assets/images/springboot.png',
          order: 2,

        },
        {
          name: 'Angular',
          description: 'Angular Kendo UI Template-Set',
          imgUrl: 'assets/images/angular.png',
          order: 3,
        },
        {
          name: 'Docker',
          description: 'Ionic Template-Set',
          imgUrl: 'assets/images/docker.png',
          order: 4,
        },
      ];

  }

}
