import { Component } from '@angular/core';
import { CreditoService } from '../services/credito.service';
import { Credito } from '../models/credito.model';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-consulta-credito',
  templateUrl: './consulta-credito.component.html',
  styleUrls: ['./consulta-credito.component.scss'],
  standalone: true,
  imports: [CommonModule, FormsModule],
})

export class ConsultaCreditoComponent {
  tipoBusca: 'credito' | 'nfse' = 'credito';
  termoBusca: string = '';
  creditos: Credito[] = [];
  erro = '';

  constructor(private creditoService: CreditoService) {}

  buscar() {
    this.erro = '';
    this.creditos = [];

    if (!this.termoBusca) {
      this.erro = 'Digite um número válido.';
      return;
    }

    if (this.tipoBusca === 'nfse') {
      this.creditoService.getByNfse(this.termoBusca).subscribe({
        next: data => this.creditos = data,
        error: err => this.erro = 'Erro ao buscar por NFS-e.'
      });
    } else {
      this.creditoService.getByNumeroCredito(this.termoBusca).subscribe({
        next: data => this.creditos = [data],
        error: err => this.erro = 'Erro ao buscar por número do crédito.'
      });
    }
  }
}
