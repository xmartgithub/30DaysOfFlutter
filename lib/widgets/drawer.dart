import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

class MyDrawer extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    final imageUrl =
        "https://avatars.githubusercontent.com/u/25157495?s=460&u=b3a3de3b12f5edc5c0702d237e8f3951924ee41d&v=4";
    return Drawer(
      child: ListView(
        children: [
          DrawerHeader(
            padding: EdgeInsets.zero,
            child: UserAccountsDrawerHeader(
              accountName: Text("Ijaz Sharif"),
              accountEmail: Text("ijazsharif34@gmail.com"),
              currentAccountPicture: CircleAvatar(
                backgroundImage: NetworkImage(imageUrl),
              ),
            ),
          ),
          ListTile(
            leading: Icon(CupertinoIcons.home),
            title: Text("Home"),
          ),
          ListTile(
            leading: Icon(CupertinoIcons.profile_circled),
            title: Text("Account"),
          ),
          ListTile(
            leading: Icon(CupertinoIcons.badge_plus_radiowaves_right),
            title: Text("About Us"),
          ),
          ListTile(
            leading: Icon(CupertinoIcons.mail),
            title: Text("Contact Us"),
          ),
        ],
      ),
    );
  }
}
