import { HttpClient, HttpHeaders } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class JustificationService {
  private apiUrl = 'http://localhost:8080/justification';
  private http: HttpClient = inject(HttpClient);

  /** 🔐 Devuelve headers con el token de JWT - SOLO Authorization */
  private getAuthHeaders(): HttpHeaders {
    const token = localStorage.getItem('token');
    if (!token) {
      console.warn('No se encontró token JWT');
      return new HttpHeaders();
    }
    
    // IMPORTANTE: NO agregues Content-Type aquí para FormData
    return new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
  }

  sendJustification(formData: FormData): Observable<any> {
    console.log('Token enviado:', localStorage.getItem('token'));
    
    // Para FormData, Angular automáticamente establece:
    // Content-Type: multipart/form-data; boundary=----WebKitFormBoundary...
    // Solo necesitamos agregar Authorization
    return this.http.post(this.apiUrl, formData, {
      headers: this.getAuthHeaders()
    });
  }

  // Método alternativo usando observe para debug completo
  sendJustificationWithDebug(formData: FormData): Observable<any> {
    return this.http.post(this.apiUrl, formData, {
      headers: this.getAuthHeaders(),
      observe: 'response' // Para ver headers de respuesta también
    });
  }
}