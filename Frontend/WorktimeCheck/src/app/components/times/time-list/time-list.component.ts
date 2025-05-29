import { Component, inject, OnInit } from '@angular/core';
import { TimeService } from '../../../services/Time/time.service';
import { PaginatedResponse } from '../../../models/paginatedResponse';
import { EmployeeTime } from '../../../models/employeeTime';
import { CommonModule, DatePipe } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-time-list',
  templateUrl: './time-list.component.html',
  styleUrls: ['./time-list.component.css'],
  standalone: true,
  imports: [DatePipe, CommonModule, FormsModule]
})
export class TimeListComponent implements OnInit {
  employeeTimes: EmployeeTime[] = [];
  size: number = 10;
  page: number = 0;
  totalPages: number = 0;
  totalPagesArray: number[] = [];
  employeeId: number = 1;

  private timeService: TimeService = inject(TimeService);
  private router: ActivatedRoute = inject(ActivatedRoute);

  ngOnInit(): void {
    this.loadTimes();
  }

  loadTimes() {
    this.timeService.getPagedEmployeeTimes(this.employeeId, this.page, this.size).subscribe({
      next: (res: PaginatedResponse<EmployeeTime>) => {
        this.employeeTimes = res.content;
        this.totalPages = res.totalPages;
        this.page = res.number;
      },
      error: (err: any) => {
        console.error('Error fetching employee times', err);
      }
    });
  }

  goToPage(pageNumber: number) {
    this.page = pageNumber;
    this.loadTimes();
  }

  previousPage() {
    if (this.page > 0) {
      this.page--;
      this.loadTimes();
    }
  }

  nextPage() {
    if (this.page < this.totalPages - 1) {
      this.page++;
      this.loadTimes();
    }
  }
}
