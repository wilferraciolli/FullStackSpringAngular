import {Component, effect, signal, ViewChild} from '@angular/core';
import {NgxInteractiveOrgChart, NgxInteractiveOrgChartTheme, OrgChartNode} from 'ngx-interactive-org-chart';
import {NgClass} from '@angular/common';


@Component({
  selector: 'wt-organisation-chart',
  imports: [NgxInteractiveOrgChart, NgClass],
  templateUrl: './organisation-chart.html',
  styleUrl: './organisation-chart.scss',
})
export class OrganisationChart {
  // Programmatically highlight nodes
  @ViewChild(NgxInteractiveOrgChart)
  orgChart!: NgxInteractiveOrgChart<any>;

  highlightManager() {
    // This ID must match an 'id' in your mapped orgChartData
    this.orgChart.highlightNode(1); // Automatically zooms to optimal level
  }

  data: ApiResponse = {
    id: 1,
    name: 'Wiltech Global Holdings',
    type: NodeTypeEnum.Organisation,
    children: [
      {
        id: 2,
        name: 'Wiltech Europe',
        type: NodeTypeEnum.OrganisationEntity,
        children: [
          {
            id: 10,
            name: 'Engineering & Product',
            type: NodeTypeEnum.Department,
            reportingJob: {
              title: 'Director of Engineering',
              person: {
                id: 501,
                name: 'Sarah Connor',
                thumbnail: 'https://randomuser.me/api/portraits/women/10.jpg',
                title: 'Director'
              }
            },
            children: [
              {
                id: 11,
                name: 'Platform Engineering', // Nested Dept 1
                type: NodeTypeEnum.Department,
                children: [
                  {
                    id: 102,
                    name: 'Lead DevOps',
                    type: NodeTypeEnum.Job,
                    status: JobStatus.Occupied,
                    person: { id: 103, name: 'Mike Torres', title: 'Principal SRE', thumbnail: 'https://randomuser.me/api/portraits/men/32.jpg', type: 'contractor' }
                  },
                  { id: 104, name: 'Cloud Architect', type: NodeTypeEnum.Job, status: JobStatus.Vacant }
                ]
              },
              {
                id: 12,
                name: 'Consumer Apps', // Nested Dept 2
                type: NodeTypeEnum.Department,
                children: [
                  {
                    id: 13,
                    name: 'Frontend Squad', // Double Nested!
                    type: NodeTypeEnum.Department,
                    children: [
                      {
                        id: 100,
                        name: 'Senior Frontend',
                        type: NodeTypeEnum.Job,
                        status: JobStatus.Occupied,
                        person: { id: 101, name: 'Alice Johnson', title: 'UI Lead', thumbnail: 'https://randomuser.me/api/portraits/women/44.jpg', type: 'employee' }
                      },
                      { id: 105, name: 'Junior Dev', type: NodeTypeEnum.Job, status: JobStatus.Vacant }
                    ]
                  }
                ]
              }
            ]
          },
          {
            id: 20,
            name: 'Operations & HR',
            type: NodeTypeEnum.Department,
            children: [
              { id: 200, name: 'HR Manager', type: NodeTypeEnum.Job, status: JobStatus.Occupied, person: { id: 201, name: 'Emma Davis', title: 'People Ops', thumbnail: 'https://randomuser.me/api/portraits/women/68.jpg' } },
              { id: 202, name: 'Recruiter', type: NodeTypeEnum.Job, status: JobStatus.Vacant }
            ]
          }
        ]
      },
      {
        id: 3,
        name: 'Wiltech Americas',
        type: NodeTypeEnum.OrganisationEntity,
        children: [
          {
            id: 30,
            name: 'Sales & Marketing',
            type: NodeTypeEnum.Department,
            children: [
              { id: 300, name: 'VP Sales', type: NodeTypeEnum.Job, status: JobStatus.Occupied, person: { id: 301, name: 'James Wilson', title: 'VP Sales', thumbnail: 'https://randomuser.me/api/portraits/men/46.jpg' } },
              { id: 302, name: 'Marketing Specialist', type: NodeTypeEnum.Job, status: JobStatus.Occupied, person: { id: 303, name: 'Sarah Lane', title: 'Growth Lead', thumbnail: 'https://randomuser.me/api/portraits/women/12.jpg' } }
            ]
          }
        ]
      }
    ]
  };

  // data: ApiResponse = {
  //   id: 1,
  //   name: 'Wiltech',
  //   type: NodeTypeEnum.Organisation,
  //   children: [
  //     {
  //       id: 2,
  //       name: 'Wiltech Europe',
  //       type: NodeTypeEnum.OrganisationEntity,
  //       children: [
  //         {
  //           id: 10,
  //           name: 'Engineering',
  //           type: NodeTypeEnum.Department,
  //           children: [
  //             {
  //               id: 100,
  //               name: 'Frontend Developer',
  //               type: NodeTypeEnum.Job,
  //               title: 'Frontend Developer',
  //               status: JobStatus.Occupied,
  //               person: {
  //                 id: 101,
  //                 name: 'Alice Johnson',
  //                 title: 'Frontend Developer',
  //                 thumbnail: 'https://randomuser.me/api/portraits/women/44.jpg',
  //                 type: 'employee'
  //               }
  //             },
  //             {
  //               id: 101,
  //               name: 'Backend Developer',
  //               type: NodeTypeEnum.Job,
  //               title: 'Backend Developer',
  //               status: JobStatus.Vacant
  //             },
  //             {
  //               id: 102,
  //               name: 'DevOps Engineer',
  //               type: NodeTypeEnum.Job,
  //               title: 'DevOps Engineer',
  //               status: JobStatus.Occupied,
  //               person: {
  //                 id: 103,
  //                 name: 'Mike Torres',
  //                 title: 'DevOps Engineer',
  //                 thumbnail: 'https://randomuser.me/api/portraits/men/32.jpg',
  //                 type: 'contractor'
  //               }
  //             }
  //           ]
  //         },
  //         {
  //           id: 20,
  //           name: 'Product & Design',
  //           type: NodeTypeEnum.Department,
  //           children: [
  //             {
  //               id: 200,
  //               name: 'Product Manager',
  //               type: NodeTypeEnum.Job,
  //               title: 'Product Manager',
  //               status: JobStatus.Occupied,
  //               person: {
  //                 id: 201,
  //                 name: 'Emma Davis',
  //                 title: 'Product Manager',
  //                 thumbnail: 'https://randomuser.me/api/portraits/women/68.jpg'
  //               }
  //             },
  //             {
  //               id: 201,
  //               name: 'UX Designer',
  //               type: NodeTypeEnum.Job,
  //               title: 'UX Designer',
  //               status: JobStatus.Vacant
  //             }
  //           ]
  //         }
  //       ]
  //     },
  //     {
  //       id: 3,
  //       name: 'Wiltech America',
  //       type: NodeTypeEnum.OrganisationEntity,
  //       children: [
  //         // ... similar structure
  //       ]
  //     }
  //   ]
  // };

  protected onNodeSelected(node: any): void {
    console.log('Clicked node:', node);
  }

  protected readonly orgChartData = signal<OrgChartNode<ApiResponse> | null>(
    null
  );

  readonly #setOrgChartData = effect(() => {
    this.orgChartData.set(this.mapDataToOrgChartNode(this.data));
  });

  protected readonly dataTypeEnum = EmployeeTypeEnum;
  protected readonly nodeTypeEnum = NodeTypeEnum;

  protected readonly themeOptions: NgxInteractiveOrgChartTheme = {
    node: {
      background: 'white',
      color: 'black',
      shadow: '0 2px 8px rgba(0, 0, 0, 0.1)',
      borderRadius: '8px',
      outlineColor: '#e0e0e0',
      activeOutlineColor: '#1976d2',
    },
  };

  private mapDataToOrgChartNode(item: ApiResponse): OrgChartNode<ApiResponse> {
    const isJob = item.type === NodeTypeEnum.Job;
    const isVacant = isJob && item.status === JobStatus.Vacant;

    // Use a composite ID: type-id (e.g., 'department-10')
    const uniqueId = `${item.type}-${item.id}`;

    return {
      id: uniqueId,
      name: item.name,           // used for search
      collapsed: item.type === NodeTypeEnum.Department,

      style: {
        '--node-background': this.getNodeBackground(item.type, isVacant),
        '--node-color': this.getNodeColor(item.type, isVacant),
        '--node-border': isVacant ? '2px dashed #f87171' : 'undefined',
      },

      nodeClass: [
        item.type.toLowerCase(),
        isJob && `job-${item.status}`,
      ].filter(Boolean).join(' '),

      data: {...item},

      children: item.children?.map(child => this.mapDataToOrgChartNode(child)) ?? [],
    };
  }

  private getNodeBackground(type: NodeTypeEnum, isVacant: boolean): string {
    if (isVacant) return '#fef2f2'; // light red

    switch(type) {
      case NodeTypeEnum.Organisation: return '#1e293b'; // Dark Slate
      case NodeTypeEnum.OrganisationEntity: return '#334155'; // Slate
      case NodeTypeEnum.Department: return '#eff6ff'; // Very light blue
      case NodeTypeEnum.Job: return '#ffffff'; // White for jobs
      default: return '#f9fafb';
    }
  }

  private getNodeColor(type: NodeTypeEnum, isVacant: boolean): string {
    if (type === NodeTypeEnum.Organisation || type === NodeTypeEnum.OrganisationEntity) {
      return '#ffffff'; // White text for dark backgrounds
    }
    return '#1f2937';
  }
}

enum NodeTypeEnum {
  Organisation = 'Organisation',
  OrganisationEntity = 'OrganisationEntity',
  Department = 'department',
  Job = 'Job'
}

enum JobStatus {
  Vacant = 'vacant',
  Occupied = 'occupied',
}

enum EmployeeTypeEnum {
  Employee = 'employee',
  Contractor = 'contractor',
}

interface Person {
  id: number;
  name: string;
  title: string;
  thumbnail?: string;
  type?: 'employee' | 'contractor';
}

interface ApiResponse {
  readonly id: number;
  name: string;
  type: NodeTypeEnum;
  // only for Job nodes
  status?: JobStatus;
  person?: Person;          // present when status = occupied
  title?: string;           // fallback / job title when vacant
  thumbnail?: string;       // only when occupied
  reportingJob?: {
    title: string;
    person: Person;
  };
  children?: ApiResponse[];
}
