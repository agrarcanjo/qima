import {Component, OnInit} from '@angular/core';
import {ProductService} from "../product/service/product.service";
import {TokenStorageService} from "../../shared/services/token-storage.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  content?: string;
  isLoggedIn = false;
  products: any = []; // Product
  categories: any = []; // Category

  constructor(private router: Router,
              private tokenStorage: TokenStorageService,
              private productService: ProductService) {
  }

  ngOnInit(): void {
    if (this.tokenStorage.getToken()) {
      this.isLoggedIn = true;
      this.getProducts();
    }
  }

  private getProducts() {
    this.productService.getAllProducts().subscribe(
      data => {
        this.products = data;
      },
      err => {
        this.content = JSON.parse(err.error).message;
      }
    )
  }

  private getCategories() {
    this.productService.getAllCategories().subscribe(
      data => {
        this.categories = data;
      },
      err => {
        this.content = JSON.parse(err.error).message;
      }
    )
  }

  getCategoryPath(categories: Category[]) {
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


createProduct() {
    this.router.navigate(['product']);
  }

  editProduct(product: any) {
    this.router.navigate(['product/' + product.id]);
  }

  deleteProduct(product: any) {
    this.productService.deleteProduct(product.id).subscribe(
      () => {
        this.getProducts();
      },
      err => {
        this.content = JSON.parse(err.error).message;
      }
    )
  }
}


export interface Product {
  id: number;
  name: string;
  description: string;
  price: number;
  available: boolean;
}

export interface Category {
  id: number;
  name: string;
  parent: Category;
}
