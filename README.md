# DSA Digital Signature Simulator

Một ứng dụng Java Swing để mô phỏng thuật toán chữ ký số DSA theo chuẩn FIPS 186-4.

## Tổng quan

Ứng dụng gồm:
- `DSAMain.java`: điểm vào chính, kiểm tra điều kiện khởi chạy và tạo GUI.
- `DSAGUI.java`: giao diện Swing cho phép sinh khóa, ký văn bản và xác minh chữ ký.
- `DSASignatureParams.java`: sinh tham số DSA chuẩn 1024/160-bit bằng Java provider.
- `DSAKeyPair.java`: lưu trữ khóa bí mật và khóa công khai.
- `DSASignature.java`: biểu diễn chữ ký DSA, serialize/deserialize.
- `DSASignatureAlgorithm.java`: triển khai quá trình sinh khóa, ký và xác minh.
- `build_run_dsa.sh`: script tiện ích để biên dịch và chạy dự án.

## Tính năng

- Sinh tham số DSA chuẩn 1024-bit p và 160-bit q.
- Sinh cặp khóa DSA: x (private) và y (public).
- Ký văn bản với SHA-256 và thuật toán DSA.
- Xác minh chữ ký DSA.
- Lưu khóa và chữ ký ra file, tải văn bản/chu ký từ file.
- Giao diện người dùng trực quan, hỗ trợ theme và các thẻ rõ ràng.

## Yêu cầu

- Java 8 trở lên.
- Swing/AWT có sẵn (không chạy ở chế độ headless).
- Môi trường macOS/Linux/Windows hỗ trợ GUI.

## Cách chạy

### Dùng shell script

```bash
cd '/Users/nguyenlinh/Documents/BTL ATBMTT/javaApps/files'
chmod +x build_run_dsa.sh
./build_run_dsa.sh
```

Hoặc:

```bash
./build_run_dsa.sh compile
./build_run_dsa.sh run
```

### Dùng javac/java trực tiếp

```bash
cd '/Users/nguyenlinh/Documents/BTL ATBMTT/javaApps/files'
javac *.java
java DSAMain
```

## Định dạng chữ ký

Chữ ký DSA được hiển thị và sử dụng theo định dạng:

```text
r=<HEX>
s=<HEX>
```

## Lưu ý

- Khóa bí mật `x` không nên chia sẻ ngoài môi trường demo.
- Chỉ chia sẻ khóa công khai `y` và tham số `p, q, g` khi cần xác minh.
- Ứng dụng phù hợp cho mục đích học tập, demo và thử nghiệm.

## Cập nhật gần đây

- Đã sửa lỗi `DSAMain` không tạo GUI khi khởi chạy.
- Đã đồng bộ định dạng chữ ký giữa hiển thị và parse trong `DSAGUI`/`DSASignature`.

## Liên hệ

Dự án thuộc bài tập lớn môn An Toàn Bảo Mật Thông Tin tại HAUI.
