# RaytracingEngine3D
A project that aims at creating a 3D ray tracer in Java

Typical parameters:

    {
        "name": "Aluminium",
        "roughness": 0.9,
        "refractionIndex": [42.42, 47.98, 47.98],
        "kSpecular": 0.1,
        "reflectivity": 0.1001,
        "transparency": 0.0
    }
    
    {
        "name": "Glass",
        "roughness": 0.0,
        "refractionIndex": [1.50917, 1.51534, 1.51690],
        "kSpecular": 0.2,
        "reflectivity": 0.2,
        "transparency": 0.8
    }

    {
        /*
         *	https://refractiveindex.info/?shelf=organic&book=polystyren&page=Sultanova
         */
        "name": "Polystyrene",
        "roughness": 0.9,
        "refractionIndex": [1.5850, 1.5972, 1.6090],
        "kSpecular": 0.1,
        "reflectivity": 0.054487,
        "transparency": 0.0
    }