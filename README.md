<div id="top"></div>

[![Contributors][contributors-shield]][contributors-url]
[![Forks][forks-shield]][forks-url]
[![Stargazers][stars-shield]][stars-url]
[![Issues][issues-shield]][issues-url]
[![MIT License][license-shield]][license-url]
[![LinkedIn][linkedin-shield]][linkedin-url]



<!-- PROJECT LOGO -->
<br />
<div align="center">
  <a href="https://github.com/azhar337/office-temperature">
    <img src="images/logo.png" alt="Logo" width="80" height="80">
  </a>

<h3 align="center">My Office Temperature</h3>

  <p align="center">
    Jump to the core of the project
    <br />
    <a href=""><strong>Explore the docs »</strong></a>
    <br />
    <br />
    <a href="https://github.com/azhar337/office-temperature/tree/main/temperatureFrontend/webapp">Frontend</a>
    ·
    <a href="https://github.com/azhar337/office-temperature/tree/main/temperatureBackend/src/main/java/org/azhar">Backend</a>
    ·
    <a href="https://github.com/azhar337/office-temperature/tree/main/temperatureBackend/src/main/java/org/azhar/prediction">Prediction Model</a>
  </p>
</div>



<!-- TABLE OF CONTENTS -->
<details>
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#about-the-project">About The Project</a>
      <ul>
        <li><a href="#built-with">Built With</a></li>
      </ul>
    </li>
    <li>
      <a href="#getting-started">Getting Started</a>
      <ul>
        <li><a href="#prerequisites">Prerequisites</a></li>
        <li><a href="#installation">Installation</a></li>
      </ul>
    </li>
    <li><a href="#usage">Usage</a></li>
    <li><a href="#license">License</a></li>
    <li><a href="#contact">Contact</a></li>
    <li><a href="#acknowledgments">Acknowledgments</a></li>
  </ol>
</details>



<!-- ABOUT THE PROJECT -->
## About The Project

![Product Name Screen Shot][product-screenshot]

This project is to help business upload, analyse, download and predict temperature of their office. It consists of 3 main part. User able to upload as much file as they want. They Can analyse the data. They can predict the upcoming temperature using neural network which is trained on provided file.

<p align="right">(<a href="#top">back to top</a>)</p>



### Built With

* [Next.js](https://nextjs.org/)
* [Quarkus](https://quarkus.io/)
* [DL4J](https://deeplearning4j.konduit.ai/)
* [Docker](https://www.docker.com/)

<p align="right">(<a href="#top">back to top</a>)</p>



<!-- GETTING STARTED -->
## Getting Started

User already have Java, Node.js and docker installed

### Prerequisites

Before starting, here is setup that needed to be configured 
* [Gmail setup guide](https://quarkus.io/guides/mailer-reference#gmail-specific-configuration )
* [Get private and public key here (generate yourself better) and put in auth folder](https://dinochiesa.github.io/jwt/)

### Getting started

1. Clone the repo
   ```sh
   git clone https://github.com/azhar337/office-temperature.git
   ```
2. Open and load all dependency for temperatureBackend and temperatureFrontend folder


3. Enter your gmail at application.properties according to the [guide](https://quarkus.io/guides/mailer-reference#gmail-specific-configuration)

4. Insert the public pem and private key [here](https://github.com/azhar337/office-temperature/tree/main/temperatureBackend/src/main/resources/auth)

5. Run the quarkus ( will use docker ) : will start run at port 8080 by default
    ```sh
   /.mvnw compile quarkus:dev
   ```

6. Run the next.js : will start run at port 3000 by default
    ```sh
   npm run dev
   ```
7. Ready to use.

<p align="right">(<a href="#top">back to top</a>)</p>



<!-- USAGE EXAMPLES -->
## Usage

Use to upload temperature file with 5 columns: `sensor id`,`row 2`,`time`,`row4`, `row 5`, `row 6`, `temperature` and get the analysis of the file. Each file will have its our prediction model that will be used to do prediction together with the neural network. Filter the data and download your preferred data but just clicking a button.    

<p align="right">(<a href="#top">back to top</a>)</p>


<!-- LICENSE -->
## License

Distributed under the MIT License.

<p align="right">(<a href="#top">back to top</a>)</p>



<!-- CONTACT -->
## Contact

Your Name - [@123](https://twitter.com/123) - 123@gmail.com

Project Link: [https://github.com/azhar337/office-temperature](https://github.com/azhar337/office-temperature)

<p align="right">(<a href="#top">back to top</a>)</p>



<!-- MARKDOWN LINKS & IMAGES -->
<!-- https://www.markdownguide.org/basic-syntax/#reference-style-links -->
[contributors-shield]: https://img.shields.io/github/contributors/github_username/repo_name.svg?style=for-the-badge
[contributors-url]: https://github.com/azhar337/office-temperature
[forks-shield]: https://img.shields.io/github/forks/github_username/repo_name.svg?style=for-the-badge
[forks-url]: https://github.com/azhar337/office-temperature
[stars-shield]: https://img.shields.io/github/stars/github_username/repo_name.svg?style=for-the-badge
[stars-url]: https://github.com/azhar337/office-temperature
[issues-shield]: https://img.shields.io/github/issues/github_username/repo_name.svg?style=for-the-badge
[issues-url]: https://github.com/azhar337/office-temperature
[license-shield]: https://img.shields.io/github/license/github_username/repo_name.svg?style=for-the-badge
[license-url]: https://github.com/azhar337/office-temperature
[linkedin-shield]: https://img.shields.io/badge/-LinkedIn-black.svg?style=for-the-badge&logo=linkedin&colorB=555
[linkedin-url]: https://www.linkedin.com/in/mohamamd-azhar-b227401a0/
[product-screenshot]: images/project.gif
