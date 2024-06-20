import {Component, OnInit} from '@angular/core';
import {ProductService} from '../product/service/product.service';
import {TokenStorageService} from '../../shared/services/token-storage.service';
import {Router} from '@angular/router';
import {FormBuilder} from '@angular/forms';
import {HttpParams} from '@angular/common/http';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  content?: string;
  isLoggedIn = false;
  products: ProductPageable | undefined; // Product
  categories: any = []; // Category
  form = {
    name: '',
    description: '',
    available: ''
  };
  sort = false;

  constructor(private router: Router,
              private fb: FormBuilder,
              private tokenStorage: TokenStorageService,
              private productService: ProductService) {
  }

  ngOnInit(): void {
    if (this.tokenStorage.getToken()) {
      this.isLoggedIn = true;
      this.getProducts(this.prepareFitler());
    }
    // this.form = this.fb.group({
    //   name: new FormControl()
    // });
  }

  private getProducts(params: HttpParams): void {
    this.productService.getAllProductsPageable(params).subscribe(
      data => {
        console.log(data);
        this.products = data;
      }
    );
  }

  getCategoryPath(categories: Category[] | undefined): string {
    let path = '';
    if (categories && categories.length > 0) {
      let category = categories[0];
      while (category) {
        path = category.name + (path ? ' -> ' + path : '');
        category = category.parent;
      }
    }
    return path;
  }


  createProduct(): void {
    this.router.navigate(['product']);
  }

  editProduct(product: any): void {
    this.router.navigate(['product/' + product.id]);
  }

  deleteProduct(product: any): void {
    this.productService.deleteProduct(product.id).subscribe(
      () => {
        this.onSubmit();
      },
      err => {
        this.content = JSON.parse(err.error).message;
      }
    );
  }

  onSubmit(): void {
    this.sort = !this.sort;
    this.getProducts(this.prepareFitler(this.sort));
  }

  private prepareFitler(sort = false): HttpParams {
    let params = new HttpParams()
      .append('page', '0')
      .append('size', '100')
      .append('sort', sort ? 'name,desc' : 'name,asc');

    if (this.form.name) {
      params = params.append('name', this.form.name);
    }
    if (this.form.description) {
      params = params.append('description', this.form.description);
    }
    if (this.form.available) {
      params = params.append('available', this.form.available);
    }
    return params;
  }
}


export interface Product {
  id: number;
  name: string;
  description: string;
  price: number;
  available: boolean;
  categories: Category[];
}

export interface Category {
  id: number;
  name: string;
  parent: Category;
}

export interface ProductPageable {
  content: Product[];
  pageable: {
    sort: {
      sorted: boolean;
      unsorted: boolean;
      empty: boolean;
    };
    offset: number;
    pageNumber: number;
    pageSize: number;
    paged: boolean;
    unpaged: boolean;
  };
}
