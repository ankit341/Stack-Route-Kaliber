/*app.component.ts*/
import { Component, OnInit } from '@angular/core';
// import * as CanvasJS from './canvasjs.min';
// var CanvasJS = require('./canvasjs.min');

// import { STOCKS } from '../shared';


export interface PeriodicElement {
  name: string;
  position: number;
  weight: number;
  symbol: string;
}

const ELEMENT_DATA: PeriodicElement[] = [
  {position: 1, name: 'Hydrogen', weight: 1.0079, symbol: 'H'},
  {position: 2, name: 'Helium', weight: 4.0026, symbol: 'He'},
  {position: 3, name: 'Lithium', weight: 6.941, symbol: 'Li'},
  {position: 4, name: 'Beryllium', weight: 9.0122, symbol: 'Be'},
  {position: 5, name: 'Boron', weight: 10.811, symbol: 'B'},
  {position: 6, name: 'Carbon', weight: 12.0107, symbol: 'C'},
  {position: 7, name: 'Nitrogen', weight: 14.0067, symbol: 'N'},
  {position: 8, name: 'Oxygen', weight: 15.9994, symbol: 'O'},
  {position: 9, name: 'Fluorine', weight: 18.9984, symbol: 'F'},
  {position: 10, name: 'Neon', weight: 20.1797, symbol: 'Ne'},
];
@Component({
  selector: 'app-progress-graph',
  templateUrl: './progress-graph.component.html',
  styleUrls: ['./progress-graph.component.css']
})
export class ProgressGraphComponent implements OnInit {
  displayedColumns: string[] = ['position', 'name', 'weight', 'symbol'];
  dataSource = ELEMENT_DATA;
  ngOnInit() { }


  // ngOnInit() {
  //   CanvasJS.addColorSet('greenShades', [
  //     // colorSet Array

  //     '#2F4F4F',
  //     '#008080',
  //     '#2E8B57',
  //     '#3CB371',
  //     '#90EE90'
  //   ]);
  //   const chart = new CanvasJS.Chart('chartContainer', {
  //     animationEnabled: true,
  //     title: {
  //       text: 'Your Score vs Total Score for each Concept'
  //     },
  //     colorSet: 'greenShades',
  //     axisY: {
  //       title: 'Total Score'
  //       // titleFontColor: '#4F81BC',
  //       // lineColor: '#4F81BC',
  //       // labelFontColor: '#4F81BC',
  //       // tickColor: '#4F81BC'
  //     },

  //     toolTip: {
  //       shared: true
  //     },
  //     legend: {
  //       cursor: 'pointer',
  //       itemclick: toggleDataSeries
  //     },
  //     data: [
  //       {
  //         type: 'column',
  //         name: 'Total Score',
  //         legendText: 'Total Score',
  //         showInLegend: true,
  //         dataPoints: [
  //           { label: 'DataTypes', y: 15 },
  //           { label: 'Array', y: 10 },
  //           { label: 'Collections', y: 25 },
  //           { label: 'Inheritance', y: 10 },
  //           { label: 'abstraction', y: 10 }
  //         ]
  //       },
  //       {
  //         type: 'column',
  //         name: 'Your Score',
  //         legendText: 'Your Score',
  //         // axisYType: 'secondary',
  //         showInLegend: true,
  //         dataPoints: [
  //           { label: 'DataTypes', y: 12 },
  //           { label: 'Array', y: 10 },
  //           { label: 'Collections', y: 15 },
  //           { label: 'Inheritance', y: 0 },
  //           { label: 'abstraction', y: 5 }
  //         ]
  //       }
  //     ]
  //   });
  //   chart.render();

  //   function toggleDataSeries(e) {
  //     if (typeof e.dataSeries.visible === 'undefined' || e.dataSeries.visible) {
  //       e.dataSeries.visible = false;
  //     } else {
  //       e.dataSeries.visible = true;
  //     }
  //     chart.render();
  //   }
  // }
}
