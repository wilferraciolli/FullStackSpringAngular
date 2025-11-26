import {ChangeDetectionStrategy, Component, OnInit, signal} from '@angular/core';
import {CommonModule} from '@angular/common';
import {OrganizationChartModule} from 'primeng/organizationchart';
import {TreeNode} from 'primeng/api';
import {MatIconModule} from '@angular/material/icon';
import {MatButtonModule} from '@angular/material/button';

@Component({
  selector: 'wt-org-chart',
  imports: [CommonModule, OrganizationChartModule, MatIconModule, MatButtonModule],
  templateUrl: './org-chart.html',
  styleUrl: './org-chart.scss',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class OrgChart implements OnInit {
  // Signal to hold the tree data
  data = signal<TreeNode[]>([]);

  ngOnInit() {
    this.data.set([
      {
        label: 'Global Org',
        type: 'ORG',
        expanded: true,
        data: {title: 'Headquarters', visibility: 'PUBLIC'},
        children: [
          {
            label: 'Board Members',
            type: 'DEPT',
            expanded: true,
            data: {
              title: 'Governance',
              reportingJob: {title: 'Chairman', name: 'Mr. X', avatar: 'face', vacant: false}
            },
            children: [
              {
                label: 'CLOSED ENTITY',
                type: 'ORG_ENTITY',
                expanded: true,
                data: {title: 'Closed Entity', visibility: 'CLOSED'},
                children: [
                  {
                    label: 'R&D Lab',
                    type: 'DEPT',
                    expanded: true,
                    data: {
                      title: 'Research',
                      reportingJob: {title: 'Lead Scientist', name: 'Dr. Smith', avatar: 'face', vacant: false}
                    },
                    // Dr. Smith is removed from children, only his subordinates remain
                    children: [
                      {label: 'Jr Researcher', type: 'JOB', data: {assignee: null, vacant: true}},
                      {label: 'Lab Assistant', type: 'JOB', data: {assignee: 'Bob', vacant: false, avatar: 'face'}}
                    ]
                  }
                ]
              },
              {
                label: 'Software Engineering',
                type: 'DEPT',
                expanded: true,
                data: {
                  title: 'Software Development',
                  reportingJob: {title: 'Software Manager', name: 'Mr. Manager', avatar: 'face', vacant: false}
                },
                children: [
                  {
                    label: 'OPEN ENTITY',
                    type: 'ORG_ENTITY',
                    expanded: true,
                    data: {title: 'Open Entity UI', visibility: 'OPEN'},
                    children: [
                      {
                        label: 'Software Engineering Frontend',
                        type: 'DEPT',
                        expanded: true,
                        data: {
                          title: 'Engineering',
                          reportingJob: {title: 'Software Manager', name: 'Dr. Wil', avatar: 'face', vacant: false}
                        },
                        children: [
                          {label: 'Jr Dev', type: 'JOB', data: {assignee: 'Olivia', vacant: false, avatar: 'face'}},
                          {
                            label: 'Angular Dev',
                            type: 'JOB',
                            data: {assignee: 'Bob', vacant: false, avatar: 'face'}
                          }
                        ]
                      }
                    ]
                  },
                  {
                    label: 'OPEN ENTITY',
                    type: 'ORG_ENTITY',
                    expanded: true,
                    data: {title: 'Open Entity API', visibility: 'OPEN'},
                    children: [
                      {
                        label: 'Software Engineering Backend',
                        type: 'DEPT',
                        expanded: true,
                        data: {
                          title: 'Engineering',
                          reportingJob: {title: 'Software Manager', name: 'Dr. Wil', avatar: 'face', vacant: false}
                        },
                        children: [
                          {
                            label: 'Jr Dev',
                            type: 'JOB',
                            data: {assignee: 'George', vacant: false, avatar: 'face'}
                          },
                          {
                            label: 'Java Dev',
                            type: 'JOB',
                            data: {assignee: 'Bob', vacant: false, avatar: 'face'}
                          }
                        ]
                      }
                    ]
                  },
                  {
                    label: 'UX & Prod Design',
                    type: 'DEPT',
                    expanded: true,
                    data: {title: 'UX and Product Design', visibility: 'OPEN'},
                    children: [
                      {
                        label: 'UX Dev',
                        type: 'JOB',
                        data: {assignee: 'Ash', vacant: false, avatar: 'face'}
                      },
                      {
                        label: 'UX Tester',
                        type: 'JOB',
                        data: {assignee: 'Mia', vacant: false, avatar: 'face'}
                      },
                      {
                        label: 'Product Designer',
                        type: 'JOB',
                        data: {assignee: 'Rebecca', vacant: false, avatar: 'face'}
                      }
                    ]
                  }
                ]
              }
            ]
          }
        ]
      }
    ]);
  }
}
