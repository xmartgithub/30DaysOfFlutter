import 'package:flutter/material.dart';
import 'package:flutter_catalog/widgets/themes.dart';
import 'package:velocity_x/velocity_x.dart';

class CartPage extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: context.canvasColor,
      appBar: AppBar(
        toolbarTextStyle: TextStyle(color: Colors.white),
        backgroundColor: Colors.transparent,
        title: "Cart".text.make(),
      ),
    );
  }
}