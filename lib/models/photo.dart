import 'dart:convert';

class PhotoModel {
  static List<Photo> photos;
}

//model class
class Photo {
  final num albumId;
  final int id;
  final String title;
  final String url;
  final String thumbnailUrl;

  Photo({
    this.albumId,
    this.id,
    this.title,
    this.url,
    this.thumbnailUrl,
  });

  // factory Photo.fromMap(Map<String, dynamic> map) {
  //   return Photo(
  //     albumId: map["albumId"],
  //     id: map["id"],
  //     title: map["title"],
  //     url: map["url"],
  //     thumbnailUrl: map["thumbnailUrl"],
  //   );
  // }

  // toMap() => {
  //       "albumId": albumId,
  //       "id": id,
  //       "title": title,
  //       "url": url,
  //       "thumbnailUrl": thumbnailUrl,
  //     };

  Photo copyWith({
    num albumId,
    int id,
    String title,
    String url,
    String thumbnailUrl,
  }) {
    return Photo(
      albumId: albumId ?? this.albumId,
      id: id ?? this.id,
      title: title ?? this.title,
      url: url ?? this.url,
      thumbnailUrl: thumbnailUrl ?? this.thumbnailUrl,
    );
  }

  Map<String, dynamic> toMap() {
    return {
      'albumId': albumId,
      'id': id,
      'title': title,
      'url': url,
      'thumbnailUrl': thumbnailUrl,
    };
  }

  factory Photo.fromMap(Map<String, dynamic> map) {
    return Photo(
      albumId: map['albumId'],
      id: map['id'],
      title: map['title'],
      url: map['url'],
      thumbnailUrl: map['thumbnailUrl'],
    );
  }

  String toJson() => json.encode(toMap());

  factory Photo.fromJson(String source) => Photo.fromMap(json.decode(source));

  @override
  String toString() {
    return 'Photo(albumId: $albumId, id: $id, title: $title, url: $url, thumbnailUrl: $thumbnailUrl)';
  }

  @override
  bool operator ==(Object other) {
    if (identical(this, other)) return true;

    return other is Photo &&
        other.albumId == albumId &&
        other.id == id &&
        other.title == title &&
        other.url == url &&
        other.thumbnailUrl == thumbnailUrl;
  }

  @override
  int get hashCode {
    return albumId.hashCode ^
        id.hashCode ^
        title.hashCode ^
        url.hashCode ^
        thumbnailUrl.hashCode;
  }
}
