import 'package:flutter/material.dart';
import 'package:thirty_day_of_flutter/utils/routes.dart';

class LoginPage extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Material(
        color: Colors.white,
        child: SafeArea(
          child: Column(
            children: [
              Image.asset(
                "assets/images/login_img.png",
                fit: BoxFit.cover,
              ),
              SizedBox(
                height: 20.0,
              ),
              Text(
                "Welcome",
                style: TextStyle(
                  fontSize: 30.0,
                  fontWeight: FontWeight.bold,
                ),
              ),
              SizedBox(
                height: 20.0,
              ),
              Padding(
                padding:
                const EdgeInsets.symmetric(vertical: 16.0, horizontal: 32.0),
                child: Column(
                  children: [
                    TextFormField(
                      decoration: InputDecoration(
                        hintText: "Enter username",
                        labelText: "Username",
                      ),
                    ),
                    TextFormField(
                      obscureText: true,
                      decoration: InputDecoration(
                        hintText: "Enter password",
                        labelText: "Password",
                      ),
                    ),
                    SizedBox(
                      height: 20.0,
                    ),
                    ElevatedButton(
                      child: Text("Login"),
                      style: TextButton.styleFrom(
                        minimumSize: Size(150,40)
                      ),
                      onPressed: () {
                        Navigator.pushNamed(context, MyRoutes.homeRoute);
                      },
                    )
                  ],
                ),
              )
            ],
          ),
        ));
  }
}
