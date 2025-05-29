import { HttpClient, HttpHeaders } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Area } from '../../models/area';

@Injectable({
  providedIn: 'root',
})
export class AreaService {
  private apiUrl: string = 'http://localhost:8080/areas';
  private http = inject(HttpClient);

  /** 🔐 Devuelve headers con el token de JWT */
  private getAuthHeaders(): HttpHeaders {
    const token = localStorage.getItem('token');
    return new HttpHeaders({
      Authorization: `Bearer ${token}`,
    });
  }

  getAreas(): Observable<Area[]> {
    return this.http.get<Area[]>(`${this.apiUrl}`, {
      headers: this.getAuthHeaders(),
    });
  }

  createArea(area: Area): Observable<Area> {
    return this.http.post<Area>(this.apiUrl, area, {
      headers: this.getAuthHeaders(),
    });
  }

  updateArea(area: Area): Observable<Area> {
    return this.http.put<Area>(`${this.apiUrl}/${area.id}`, area, {
      headers: this.getAuthHeaders(),
    });
  }

  deleteArea(areaId: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${areaId}`, {
      headers: this.getAuthHeaders(),
    });
  }
}
