# Hướng Dẫn Test Hệ Thống Phân Quyền ASHOP

## Tổng Quan Hệ Thống Filter

### 🔐 **Các Filter Đã Implement:**

1. **AuthFilter** - Kiểm tra đăng nhập cho tất cả URL cần xác thực
2. **UserFilter** - Chỉ cho phép USER truy cập `/user/*`
3. **AdminFilter** - Chỉ cho phép ADMIN truy cập `/admin/*`

### 📋 **Cấu Trúc URL:**

#### **URL Công Khai (Không cần đăng nhập):**
- `/` - Trang chủ
- `/login` - Đăng nhập
- `/register` - Đăng ký
- `/logout` - Đăng xuất

#### **URL Dành Cho USER:**
- `/user/dashboard` - Dashboard người dùng
- `/user/profile` - Thông tin cá nhân
- `/user/orders` - Đơn hàng của tôi

#### **URL Dành Cho ADMIN:**
- `/admin/dashboard` - Dashboard quản trị
- `/admin/categories` - Quản lý danh mục
- `/admin/products` - Quản lý sản phẩm
- `/admin/users` - Quản lý người dùng
- `/admin/videos` - Quản lý video

## 🧪 **Cách Test Hệ Thống:**

### **Test Case 1: User Thông Thường**
1. **Đăng nhập với tài khoản USER:**
   - Truy cập `/login`
   - Nhập username/password của user
   - ✅ **Kết quả mong đợi:** Redirect đến `/user/dashboard`

2. **Kiểm tra quyền truy cập USER:**
   - ✅ `/user/dashboard` - Có thể truy cập
   - ✅ `/user/profile` - Có thể truy cập
   - ✅ `/user/orders` - Có thể truy cập
   - ❌ `/admin/dashboard` - Bị chặn, redirect về `/`
   - ❌ `/admin/categories` - Bị chặn, redirect về `/`

### **Test Case 2: Admin**
1. **Đăng nhập với tài khoản ADMIN:**
   - Truy cập `/login`
   - Nhập username/password của admin
   - ✅ **Kết quả mong đợi:** Redirect đến `/admin/dashboard`

2. **Kiểm tra quyền truy cập ADMIN:**
   - ✅ `/admin/dashboard` - Có thể truy cập
   - ✅ `/admin/categories` - Có thể truy cập
   - ✅ `/admin/products` - Có thể truy cập
   - ✅ `/admin/users` - Có thể truy cập
   - ✅ `/admin/videos` - Có thể truy cập (THÊM VIDEO)
   - ✅ `/user/dashboard` - Có thể truy cập (Admin có thể truy cập user)
   - ✅ `/user/profile` - Có thể truy cập

### **Test Case 3: Chưa Đăng Nhập**
1. **Truy cập URL cần xác thực:**
   - ❌ `/user/dashboard` - Redirect đến `/login`
   - ❌ `/admin/dashboard` - Redirect đến `/login`
   - ❌ `/user/profile` - Redirect đến `/login`
   - ✅ `/` - Có thể truy cập
   - ✅ `/login` - Có thể truy cập
   - ✅ `/register` - Có thể truy cập

## 🔧 **Cấu Hình Filter:**

### **web.xml Configuration:**
```xml
<!-- AuthFilter: Kiểm tra đăng nhập -->
<filter-mapping>
    <filter-name>AuthFilter</filter-name>
    <url-pattern>/user/*</url-pattern>
</filter-mapping>

<!-- UserFilter: Chỉ cho phép USER -->
<filter-mapping>
    <filter-name>UserFilter</filter-name>
    <url-pattern>/user/*</url-pattern>
</filter-mapping>

<!-- AdminFilter: Chỉ cho phép ADMIN -->
<filter-mapping>
    <filter-name>AdminFilter</filter-name>
    <url-pattern>/admin/*</url-pattern>
</filter-mapping>
```

### **Sitemesh Layout:**
- `/user/*` → `user.jsp` layout
- `/admin/*` → `admin.jsp` layout
- `/*` → `web.jsp` layout

## 📝 **Ghi Chú Quan Trọng:**

1. **Thứ tự Filter:** AuthFilter chạy trước UserFilter/AdminFilter
2. **Admin có quyền cao nhất:** Admin có thể truy cập cả user và admin URL
3. **User chỉ được truy cập user URL:** User không thể truy cập admin URL
4. **Session Management:** User object được lưu trong session với key "currentUser"

## 🚀 **Deploy và Test:**

1. **Build project:**
   ```bash
   mvn clean compile
   ```

2. **Deploy lên server:**
   ```bash
   mvn clean package
   ```

3. **Tạo tài khoản test:**
   - Tạo user với role "USER"
   - Tạo user với role "ADMIN"

4. **Test theo các test case trên**

## ✅ **Kết Quả Mong Đợi:**

- ✅ User chỉ truy cập được `/user/*`
- ✅ Admin truy cập được cả `/admin/*` và `/user/*`
- ✅ Chưa đăng nhập bị redirect về `/login`
- ✅ Layout khác nhau cho user và admin
- ✅ Hệ thống bảo mật hoàn chỉnh theo role
