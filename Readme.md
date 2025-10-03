# Food Donation Application

A comprehensive web application built with Spring Boot that connects food donors with receivers to reduce food waste and help communities in need. The platform enables restaurants, individuals, and organizations to donate surplus food while allowing NGOs and individuals to find available food donations in their area.

## Features

### 🍽️ Core Functionality

- **Food Donation Management**: Donors can create, edit, and manage food offers with detailed descriptions, quantities, and pickup locations
- **Image Support**: Upload multiple photos of food items to make offers more appealing
- **Real-time Status Tracking**: Track offer status (Active, Collected, Expired, Deleted)
- **Collection System**: Receivers can mark food as collected, preventing duplicate claims
- **Location-based Matching**: GPS coordinates for precise pickup locations


### 👥 User Roles

- **Donors**: Create and manage food offers, track donation history
- **Receivers**: Browse available offers, mark items as collected
- **Admins**: Oversee platform activities and manage users


### 🎨 Professional UI

- Responsive Bootstrap-based design
- Interactive image galleries with lightbox functionality
- Real-time search and filtering
- Mobile-friendly interface
- Professional navigation and user feedback systems


## Technologies Used

- **Backend**: Spring Boot 3.x, Spring Security, Spring Data JPA
- **Frontend**: Thymeleaf, Bootstrap 5, jQuery, Font Awesome
- **Database**: MySQL with Hibernate ORM
- **Authentication**: BCrypt password hashing, role-based access control
- **File Upload**: Multipart file handling for image uploads
- **Build Tool**: Maven


## Prerequisites

- Java 17 or higher
- MySQL 8.0 or higher
- Maven 3.6+
- Modern web browser


## Installation \& Setup

### 1. Clone the Repository

```bash
git clone https://github.com/yourusername/food-donation-app.git
cd food-donation-app
```


### 2. Database Setup

```sql
-- Create database
CREATE DATABASE food_donation;

-- Create user (optional)
CREATE USER 'food_user'@'localhost' IDENTIFIED BY 'password123';
GRANT ALL PRIVILEGES ON food_donation.* TO 'food_user'@'localhost';
FLUSH PRIVILEGES;
```


### 3. Configure Application Properties

Create/update `src/main/resources/application.properties`:

```properties
# Database Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/food_donation
spring.datasource.username=food_user
spring.datasource.password=password123
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# JPA/Hibernate Configuration
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect

# File Upload Configuration
spring.servlet.multipart.max-file-size=5MB
spring.servlet.multipart.max-request-size=25MB

# Thymeleaf Configuration
spring.thymeleaf.cache=false
```


### 4. Build and Run

```bash
# Build the application
mvn clean install

# Run the application
mvn spring-boot:run
```


### 5. Access the Application

- Open your browser and navigate to: `http://localhost:8080`
- The application will automatically create necessary database tables on first run


## Default Admin Account

The application automatically creates an admin user on startup:

- **Email**: `admin@fooddonation.com`
- **Password**: `admin123`

**⚠️ Important**: Change the default admin password after first login!

## User Guide

### For Donors

1. **Register** with role "Food Donor"
2. **Login** and navigate to "My Offers"
3. **Create New Offer** with food details and photos
4. **Manage** your offers (edit, delete, track status)
5. **Monitor** when food gets collected

### For Receivers

1. **Register** with role "Food Receiver"
2. **Browse** available food offers
3. **Contact** donors for pickup coordination
4. **Mark as Collected** after receiving food

### For Admins

1. **Login** with admin credentials
2. **Manage** users and monitor platform activity
3. **Oversee** donation processes

## Project Structure

```
src/main/java/com/fooddonation/app/
├── controller/          # REST controllers
├── entity/             # JPA entities
├── repository/         # Data access layer
├── service/           # Business logic
├── config/            # Security and app configuration
└── FoodDonationApplication.java

src/main/resources/
├── templates/         # Thymeleaf templates
│   ├── fragments/     # Reusable template fragments
│   ├── donor/         # Donor-specific pages
│   ├── receiver/      # Receiver-specific pages
│   └── admin/         # Admin-specific pages
├── static/
│   ├── css/          # Custom stylesheets
│   ├── js/           # JavaScript files
│   └── images/       # Static images
└── application.properties
```


## Key Features Implementation

### Authentication \& Security

- Spring Security with role-based access control
- BCrypt password encryption
- Session management with logout functionality


### File Upload System

- Multipart file handling for food images
- Automatic directory creation for organized storage
- Image preview and lightbox functionality


### Status Management

- Comprehensive status tracking (ACTIVE, COLLECTED, EXPIRED, DELETED)
- Prevents duplicate claims on collected items
- Automatic status updates based on expiry dates


## Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## Future Enhancements

- [ ] Real-time notifications system
- [ ] Advanced search with location radius
- [ ] Rating system for donors and receivers
- [ ] Mobile application
- [ ] Email notifications
- [ ] Analytics dashboard
- [ ] Multi-language support


## Support

If you encounter any issues or have questions:

1. Check the [Issues](https://github.com/yourusername/food-donation-app/issues) page
2. Create a new issue with detailed description
3. Contact the development team

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Acknowledgments

- Spring Boot community for excellent documentation
- Bootstrap team for responsive UI components
- Contributors and testers who helped improve the application

***

**Made with ❤️ to reduce food waste and help communities in need**
<span style="display:none">[^1][^10][^2][^3][^4][^5][^6][^7][^8][^9]</span>

<div style="text-align: center">⁂</div>

[^1]: https://github.com/adiimated/Foodo

[^2]: https://github.com/aayush301/Food-Aid

[^3]: https://zenodo.org/records/15582897

[^4]: https://www.jotform.com/app-templates/food-donation-app

[^5]: https://www.ijprems.com/uploadedfiles/paper/issue_4_april_2025/40131/final/fin_ijprems1745050416.pdf

[^6]: https://www.studocu.com/in/document/university-of-mumbai/bsc-information-technology/waste-food-donation-2/32579044

[^7]: https://www.slideshare.net/slideshow/food-donation-project-report-ii/249974874

[^8]: https://www.figma.com/community/file/1396816875531251907/food-donation-app

[^9]: https://www.academia.edu/95144981/Food_Donation_Application_Software_Development_Life_Cycle_Case_Study

[^10]: https://www.scribd.com/presentation/867876667/Waste-Food-Management-Donation-Application

