import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

const API_URL = 'http://localhost:8080/api/v1/products/';
const API_URL_CATEGORY = 'http://localhost:8080/api/v1/categories/';

@Injectable({
  providedIn: 'root'
})
export class ProductService {
  constructor(private http: HttpClient) { }

  getAllProducts(): Observable<any> {
    return this.http.get(API_URL + 'all');
  }

  getProduct(id: any): Observable<any> {
    return this.http.get(API_URL + id);
  }

  createProduct(product: any): Observable<any> {
    return this.http.post(API_URL + 'add', product);
  }

  editProduct(product: any): Observable<any> {
    return this.http.put(API_URL + product.id, product);
  }

  deleteProduct(id: number): Observable<any> {
    return this.http.delete(API_URL + id);
  }

  getAllCategories(): Observable<any> {
    return this.http.get(API_URL_CATEGORY + 'all');
  }
}
