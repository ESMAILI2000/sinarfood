// App Sections / User Roles
enum class AppSection {
    General,
    Student,
    Staff,
    Citizen
}

enum class GeneralRoutes {
     Splash,
     Login,
     AboutUs,
    Exchange,
    Otp,
    ChangePassword,
    InformationPay,
    HistoryTransaction,
    WalletWords
}

enum class StudentRoutes {
     Home,
    ReservationFood
}

enum class StaffRoutes {
    Home,
}

enum class CitizenRoutes {
    Home,
}
