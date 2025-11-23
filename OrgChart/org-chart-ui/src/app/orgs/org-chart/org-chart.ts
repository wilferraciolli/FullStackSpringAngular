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
        label: 'Global Corp',
        type: 'ORG',
        expanded: true,
        data: { title: 'Headquarters', visibility: 'PUBLIC', isRoot: true },
        children: [
          {
            label: 'Board Members',
            type: 'DEPT',
            expanded: true,
            // DATA CHANGE: Added reportingJob here
            data: {
              title: 'Governance',
              reportingJob: { title: 'Chairman', name: 'Mr. X', avatar: 'gavel', vacant: false }
            },
            children: [
              {
                label: 'CLOSED ENTITY',
                type: 'ORG',
                expanded: true,
                data: { title: 'Closed Entity', visibility: 'CLOSED', isRoot: false },
                children: [
                  {
                    label: 'R&D Lab',
                    type: 'DEPT',
                    expanded: true,
                    // DATA CHANGE: Dr. Smith is now EMBEDDED here
                    data: {
                      title: 'Research',
                      reportingJob: { title: 'Lead Scientist', name: 'Dr. Smith', avatar: 'science', vacant: false }
                    },
                    // Dr. Smith is removed from children, only his subordinates remain
                    children: [
                      { label: 'Jr Researcher', type: 'JOB', data: { assignee: null, vacant: true } },
                      { label: 'Lab Assistant', type: 'JOB', data: { assignee: 'Bob', vacant: false, avatar: 'face_4' } }
                    ]
                  }
                ]


              },
              {
                label: 'Other Department',
                type: 'DEPT',
                expanded: true,
                data: {
                  title: 'Governance',
                  reportingJob: { title: 'Some Manager', name: 'Mr. X', avatar: 'gavel', vacant: false }
                },
                children: [
                  {
                    label: 'OPEN ENTITY',
                    type: 'ORG',
                    expanded: true,
                    data: { title: 'Open Entity', visibility: 'PUBLIC', isRoot: false },
                    children: [
                      {
                        label: 'R&D Lab',
                        type: 'DEPT',
                        expanded: true,
                        // DATA CHANGE: Dr. Smith is now EMBEDDED here
                        data: {
                          title: 'Research',
                          reportingJob: { title: 'Lead Scientist', name: 'Dr. Smith', avatar: 'science', vacant: false }
                        },
                        // Dr. Smith is removed from children, only his subordinates remain
                        children: [
                          { label: 'Jr Researcher', type: 'JOB', data: { assignee: null, vacant: true } },
                          { label: 'Lab Assistant', type: 'JOB', data: { assignee: 'Bob', vacant: false, avatar: 'face_4' } }
                        ]
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
