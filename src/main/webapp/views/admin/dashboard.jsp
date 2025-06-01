<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<html>
<head>
    <title><spring:message code="dashboard.title" text="Dashboard de Administración"/></title>
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background-color: #f4f6f9;
            margin: 0;
            padding: 30px;
            color: #333;
        }

        h2 {
            color: #2c3e50;
            text-align: center;
            margin-top: 40px;
            margin-bottom: 20px;
            font-size: 22px;
        }

        p {
            text-align: center;
            font-size: 18px;
            margin-bottom: 40px;
        }

        strong {
            color: #007bff;
        }

        .chart-container {
            width: 90%;
            max-width: 800px;
            margin: 0 auto 50px auto;
            background: #fff;
            padding: 25px;
            border-radius: 12px;
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.08);
            transition: box-shadow 0.3s ease;
        }

        .chart-container:hover {
            box-shadow: 0 6px 18px rgba(0, 0, 0, 0.15);
        }

        canvas {
            width: 100% !important;
            height: auto !important;
        }

    </style>
</head>
<body>

    <div>
        <h2><spring:message code="dashboard.comments.title" text="Comentarios" /></h2>
        <p>
            <spring:message code="dashboard.comments.avg" text="Número medio de comentarios por artículo:" />
            <strong><c:out value="${avgCommentsPerArticle}" /></strong>
        </p>
    </div>

    <div>
        <h2><spring:message code="dashboard.articles.title" text="Artículos con más comentarios" /></h2>
        <div class="chart-container">
            <canvas id="commentsChart"></canvas>
        </div>
        <script>
            const ctx = document.getElementById('commentsChart').getContext('2d');
            const chart = new Chart(ctx, {
                type: 'bar',
                data: {
                    labels: [
                        <c:forEach items="${mostCommentedArticles}" var="article" varStatus="loop">
                            "<c:out value='${article.title}'/>"<c:if test="${!loop.last}">,</c:if>
                        </c:forEach>
                    ],
                    datasets: [{
                        label: '<spring:message code="dashboard.articles.chart.label" text="Número de comentarios" />',
                        data: [
                            <c:forEach items="${mostCommentedArticles}" var="article" varStatus="loop">
                                ${article.comments.size()}<c:if test="${!loop.last}">,</c:if>
                            </c:forEach>
                        ],
                        backgroundColor: 'rgba(54, 162, 235, 0.6)',
                        borderColor: 'rgba(54, 162, 235, 1)',
                        borderWidth: 1
                    }]
                },
                options: {
                    responsive: true,
                    scales: {
                        y: {
                            beginAtZero: true,
                            ticks: {
                                stepSize: 1
                            }
                        }
                    }
                }
            });
        </script>
    </div>
</body>
</html>