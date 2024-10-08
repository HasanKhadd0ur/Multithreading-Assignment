# Multi Threading Assignement 


This repository contains a Java project developed as a homework assignment demonstrating multithreading techniques for efficient prime number finding and parallel image processing.

----

## Table of Contents

- [Project Structure](#project-structure)
- [Image Processing  Optimaizing Latency](#image-processing-optimaizing-latency)
  - [Image Recoloring Overview](#image-recoloring-overview)
  - [Classes Used](#classes-used-in-image-processing)
- [Prime Number Finding](#prime-number-finding)
  - [Prime Number Finding Overview](#prime-number-finding-overview)
  - [Classes Used](#classes-used-in-prime-number-finding)
- [Installation and Setup](#installation-and-setup)

---

## Project Structure

```
src
│
└───main
    └───java
        └───multithreading
            ├───imageProcessing
            │   ├───coordinators
            │   │   └───ThreadsCoordinator.java
            │   ├───helpers
            │   │   └───ColorHelper.java
            │   │   └───ImageLoadingHelper.java
            │   ├───services
            │   │   └───ImageRecolorService.java
            │   ├───workers
            │   │   └───ImageRecoloringWorker.java
            │   └───Main.java
            └───primesFinder
                ├───abstraction
                │   └───PrimesFindStrategy.java
                ├───coordinators
                │   └───ThreadsCoordinator.java
                ├───services
                │   └───PrimeNumberService.java
                ├───strategies
                │   └───SieveOfAtkinStrategy.java
                │   └───SieveOfEratosthenesStrategy.java
                │   └───TrivialPrimesFindStrategy.java
                ├───workers
                │   └───PrimeFindingWorker.java
                └───Main.java
         
```

---

## Image Processing Optimizing Latency

### Image Recoloring Overview

This module is designed to optimize the latency the old provided code by using multiple threads. Each thread is responsible for processing a portion of the image, either through horizontal slices or blocks, to optimize performance.

### Classes Used in Image Processing

- **ThreadsCoordinator**: This class coordinates the threads to ensure they process different portions of the image. It supports both horizontal slicing and block-based image processing.
  
- **ImageRecolorService**: Handles the recoloring logic for specific parts of the image, delegating actual pixel recoloring to helper methods.

- **ImageRecoloringWorker**: Represents a single unit of work for the threads. Each worker is assigned a part of the image to recolor.

- **ColorHelper**: A utility class responsible for changing the color of individual pixels.

---

## Prime Number Finding

### Prime Number Finding Overview

This module implements strategies to find prime numbers using multithreading. The goal is to divide the task of prime number computation across multiple threads to improve performance for large ranges.

### Classes Used in Prime Number Finding

- **PrimesFindStrategy**: Abstract class (interface) that defines the strategy for finding prime numbers. Different strategies can extend this class.
  
- **ThreadsCoordinator**: Coordinates the threads for prime number finding, distributed the work evenly among threads.

- **PrimeNumberService**: Manages the logic of prime number calculations.

- **PrimeFindingWorker**: Represents a worker thread responsible for finding primes within a specific range.

---

## Installation and Setup

1. Clone the repository:
   ```bash
   git clone <repository-url>
   ```

2. Navigate to the project directory:
   ```bash
   cd Multithreading-Assignment
   ```

3. Ensure that Java and a compatible IDE (such as IntelliJ IDEA) are installed.

4. Open the project in IntelliJ IDEA and resolve any dependencies.

---

## Contribution

Feel free to fork this project, make improvements, and submit pull requests. All contributions are welcome.

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for more details.

