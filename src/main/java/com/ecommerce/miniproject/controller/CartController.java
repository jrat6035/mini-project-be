package com.ecommerce.miniproject.controller;

import com.ecommerce.miniproject.dto.CartDTO;
import com.ecommerce.miniproject.dto.ResponseObject;
import com.ecommerce.miniproject.model.Cart;
import com.ecommerce.miniproject.service.CartService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/${api.path.carts}")
public class CartController extends AbstractController{
    @Autowired
    private CartService cartService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity<ResponseObject> createCart(@Valid @RequestBody CartDTO cartDTO) {
        Cart cart = modelMapper.map(cartDTO, Cart.class);
        return sendCreatedResponse(cartService.createCart(cart));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getCartById(@PathVariable String id) {
        return sendFoundResponse(cartService.getCartById(id));
    }

    @PutMapping("/{id}")
    public  ResponseEntity<ResponseObject> updateCart(@PathVariable String id, @Valid @RequestBody CartDTO cartDTO) {
        Cart cart = modelMapper.map(cartDTO, Cart.class);
        return sendSuccessResponse(cartService.updateCart(id, cart));
    }
}
