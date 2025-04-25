@Barcelo
Feature: Reserva de hotel en la web de Barceló

  Scenario Outline: Buscar hotel y acceder a la página de reservas
    Given que el usuario accede a la web de Barceló
    When acepta las cookies si aparecen
    And busca el hotel "<hotelName>"
    And selecciona fechas con <diasEntradaOffset> días desde hoy y estancia de <diasEstancia> días
    And selecciona <adultos> adultos y <ninos> niños de <edadNino> años
    And hace clic en el botón de buscar
    Then se muestra la pestaña de reservas del hotel

    Examples:
      | hotelName        | diasEntradaOffset | diasEstancia | adultos | ninos | edadNino |
      | Barceló Sants    | 5                 | 5            | 2       | 1     | 11       |
      | Barceló Marbella | 7                 | 7            | 4       | 1     | 8        |
