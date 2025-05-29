import { Component, inject, Input, OnInit } from '@angular/core';
import {
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { JustificationService } from '../../../services/Justification/justification.service';
import { DatePipe } from '@angular/common';
import { TimeJustifcation } from '../../../models/timeJustification';

@Component({
  selector: 'app-justification-form',
  imports: [DatePipe, ReactiveFormsModule],
  templateUrl: './justification-form.component.html',
  styleUrl: './justification-form.component.css',
})
export class JustificationFormComponent implements OnInit {
  @Input() selectedDate!: string;
  @Input() timeId!: number;
  justificationForm!: FormGroup;
  selectedFile: File | null = null;
  
  private fb: FormBuilder = inject(FormBuilder);
  private justificationService: JustificationService = inject(JustificationService);

  ngOnInit(): void {
    this.justificationForm = this.fb.group({
      justificationObservation: ['', Validators.required],
    });
    
    // Solo asignar si no viene por @Input
    if (!this.timeId) {
      this.timeId = 1;
    }
  }

  onFileSelected(event: any): void {
    const file = event.target.files[0];
    if (file) {
      this.selectedFile = file;
      console.log('Archivo seleccionado:', file.name, 'Tipo:', file.type, 'Tamaño:', file.size);
    }
  }

  submitJustification(): void {
    if (!this.justificationForm.valid) {
      console.error('Formulario no válido');
      alert('Por favor completa todos los campos requeridos');
      return;
    }

    if (!this.selectedFile) {
      console.error('No se ha seleccionado archivo');
      alert('Por favor selecciona un archivo');
      return;
    }

    const justificationDto = {
      timeId: this.timeId,
      justificationObservation: this.justificationForm.value.justificationObservation,
    };

    console.log('Justification DTO a enviar:', justificationDto);
    console.log('Archivo a enviar:', this.selectedFile.name);

    const formData = new FormData();
    
    // Agregar el archivo
    formData.append('file', this.selectedFile);
    
    // Agregar el JSON como Blob
    formData.append(
      'justification',
      new Blob([JSON.stringify(justificationDto)], { 
        type: 'application/json' 
      })
    );

    // Debug: ver el contenido del FormData
    console.log('FormData creado:');
    for (let pair of formData.entries()) {
      console.log(pair[0], pair[1]);
    }

    this.justificationService.sendJustification(formData).subscribe({
      next: (res: any) => {
        console.log('Respuesta del servidor:', res);
        alert('Justificación enviada correctamente');
        this.resetForm();
      },
      error: (err: any) => {
        console.error('Error completo:', err);
        const errorMessage = err.error?.message || err.message || 'Error desconocido';
        alert('Error al enviar justificación: ' + errorMessage);
      },
    });
  }

  private resetForm(): void {
    this.justificationForm.reset();
    this.selectedFile = null;
    // Reset file input
    const fileInput = document.getElementById('file') as HTMLInputElement;
    if (fileInput) {
      fileInput.value = '';
    }
  }

  cancel(): void {
    this.resetForm();
    // Aquí puedes agregar lógica adicional para cancelar
  }
}