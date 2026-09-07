
// Concept Note:https://docs.google.com/document/d/1uKHIrF-I9lyKooJW07sJNV6yizqO6ZpJ/edit
// Group 1 - Abraham Kwikiriza(191866), Mapelu Joe(188741), Boniface Mwangi(189728), Dollan Jame Ochele(193197), Venus Kabera(166434), Brian Omoto(192885)

/*
 MamaCare - Maternal Health Companion
 Thematic Area: Health and Wellness for All

 */

fun main() {

    // Variables for the mother's profile.
    var motherName = ""
    var motherAge = 0
    var motherPhone = ""
    var isRegistered = false

    // A list to store the supplements the mother adds.

    val supplementList = mutableListOf<String>()

    println("........................................")
    println("   MAMACARE - MATERNAL HEALTH COMPANION")
    println("........................................")
    println("Helping expectant and new mothers manage")
    println("prenatal and postnatal care.")
    println(".........................................")

    var running = true

    // The while loop keeps showing the menu until the user chooses to exit
    while (running) {

        println(".....................................")
        println("MAIN MENU")
        println(".....................................")
        println("1. Register my profile")
        println("2. View my registration")
        println("3. Add a prescribed supplement")
        println("4. View all my supplements")
        println("5. View nutritional needs by trimester")
        println("6. Exit")
        println(".....................................")
        print("Enter your choice: ")

        val choice = readLine()

        // when statement  based on the menu choice
        when (choice) {

            "1" -> {
                //  Register profile

                print("Enter your name: ")
                val name = readLine()

                if (name == null || name == "") {
                    println("Name cannot be empty. Please try again.")
                } else {
                    print("Enter your age: ")
                    val ageText = readLine()
                    val age = ageText?.toIntOrNull()

                    if (age == null || age < 10 || age > 60) {
                        println("Invalid age. Please enter a number between 10 and 60.")
                    } else {
                        print("Enter your phone number: ")
                        val phone = readLine()

                        if (phone == null || phone == "") {
                            println("Phone number cannot be empty.")
                        } else {

                            motherName = name
                            motherAge = age
                            motherPhone = phone
                            isRegistered = true

                            println("Registration successful! Welcome, $motherName.")
                        }
                    }
                }
            }

            "2" -> {
                //  View registration
                println("MY REGISTRATION DETAILS")

                if (isRegistered) {
                    println("Name  : $motherName")
                    println("Age   : $motherAge")
                    println("Phone : $motherPhone")
                } else {
                    println("You have not registered yet. Please choose option 1 first.")
                }
            }

            "3" -> {
                // ---- Add supplement ----
                println("ADD PRESCRIBED SUPPLEMENT\n")

                if (!isRegistered) {
                    println("Please register your profile first (option 1).")
                } else {
                    print("Enter supplement name (e.g. Iron): ")
                    val supplementName = readLine()

                    print("Enter dosage instructions (e.g. 1 tablet daily): ")
                    val dosage = readLine()

                    if (supplementName == null || supplementName == "" ||
                        dosage == null || dosage == "") {
                        println("Supplement name and dosage cannot be empty.")
                    } else {
                        // Add the new supplement to our list
                        supplementList.add("$supplementName - $dosage")
                        println("\"$supplementName\" has been added to your list.")
                    }
                }
            }

            "4" -> {
                // supplements
                println("MY PRESCRIBED SUPPLEMENTS\n")

                if (supplementList.isEmpty()) {
                    println("You have no supplements recorded yet.")
                } else {
                    // for loop to go through the list and number each item
                    var count = 1
                    for (item in supplementList) {
                        println("$count. $item")
                        count = count + 1
                    }
                }
            }

            "5" -> {
                //  Nutritional needs by trimester
                println("NUTRITIONAL NEEDS BY TRIMESTER\n")
                println("1. First Trimester")
                println("2. Second Trimester")
                println("3. Third Trimester")
                print("Which trimester are you in? ")

                val trimesterText = readLine()
                val trimester = trimesterText?.toIntOrNull()

                if (trimester == 1) {
                    println("Focus on folic acid, iron and staying hydrated.")
                    println("Eat small, frequent meals to help with morning sickness.")
                } else if (trimester == 2) {
                    println("Increase calcium and protein for bone and muscle growth.")
                    println("Keep taking iron and eat more fibre to avoid constipation.")
                } else if (trimester == 3) {
                    println("Increase vitamin K and healthy fats in your diet.")
                    println("Eat smaller meals more often and rest well.")
                } else {
                    println("Invalid choice. Please enter 1, 2 or 3.")
                }
            }

            "6" -> {
                // Exit
                println("Thank you for using MamaCare. Take care of yourself and your baby!")
                running = false
            }

            else -> {
                println("Invalid option. Please enter a number between 1 and 6.")
            }
        }
    }
}
 
